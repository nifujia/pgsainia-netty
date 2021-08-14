import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author nifujia
 * @date 2021/8/14 21:36
 * @description
 */
public class NettyServerTest {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(NettyServerTest.class);
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workEventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.AUTO_READ, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            socketChannel.pipeline().addLast(new StringEncoder(Charset.forName("GBK")));
                            socketChannel.pipeline().addLast(new StringDecoder(Charset.forName("GBK")));
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    log.info("client 接受信息：{}", msg);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    ctx.close();
                                    log.error(cause.getMessage());
                                }
                            });
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9999).syncUninterruptibly();
            log.info("netty client is started....");
            String message = String.format("我是 client ，很高兴认识你.... %s", "\r\n");
            // 向服务端发送信息
            channelFuture.channel().writeAndFlush(message);
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workEventLoopGroup.shutdownGracefully();
        }
    }
}