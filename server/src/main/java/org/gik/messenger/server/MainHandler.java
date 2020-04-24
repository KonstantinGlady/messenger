package org.gik.messenger.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());
        ctx.writeAndFlush(ctx.channel().id() + "connected.\n");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        for (Channel c : channels) {
            if (c != ctx.channel()) {
                ctx.writeAndFlush("[" + ctx.channel().remoteAddress() + "]: " + msg + "\n");
            } else {
                ctx.writeAndFlush("[ me ]: " + msg + "\n");
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
