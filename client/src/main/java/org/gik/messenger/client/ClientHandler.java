package org.gik.messenger.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    Callback onMsgReceivedCallback;
    public ClientHandler(Callback onMessageReceivedCallback) {
      this.onMsgReceivedCallback = onMessageReceivedCallback;
    }

    /*@Override
    public void channelActive(ChannelHandlerContext ctx) {
       // ctx.writeAndFlush("test\r\n");
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        if (onMsgReceivedCallback != null) {
            onMsgReceivedCallback.callback(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
