package org.gik.messenger.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NetWork {
    private static final String HOST = "localhost";
    private static final int PORT = 8182;
    private SocketChannel channel;

    public NetWork(Callback onMessageReceivedCallback) {
        new Thread(() -> {
            EventLoopGroup group = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                channel = ch;
                                ch.pipeline().addLast(
                                        new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter())
                                        , new StringDecoder()
                                        , new StringEncoder()
                                        , new ClientHandler(onMessageReceivedCallback)
                                );
                            }
                        });
                b.connect(HOST, PORT).sync().channel().closeFuture().sync();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                group.shutdownGracefully();
            }
        }).start();
    }

    public void close() {
        channel.close();
    }

    public void sendMessage(String text) {
        channel.writeAndFlush(text + "\n");
    }
}
