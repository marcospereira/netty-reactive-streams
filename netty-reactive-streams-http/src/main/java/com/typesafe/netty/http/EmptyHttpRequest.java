package com.typesafe.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;

class EmptyHttpRequest extends DelegateHttpRequest implements FullHttpRequest {

    public EmptyHttpRequest(HttpRequest request) {
        super(request);
    }

    @Override
    public FullHttpRequest setUri(String uri) {
        super.setUri(uri);
        return this;
    }

    @Override
    public FullHttpRequest setMethod(HttpMethod method) {
        super.setMethod(method);
        return this;
    }

    @Override
    public FullHttpRequest setProtocolVersion(HttpVersion version) {
        super.setProtocolVersion(version);
        return this;
    }

    @Override
    public FullHttpRequest copy() {
        if (request instanceof FullHttpRequest) {
            return new EmptyHttpRequest(((FullHttpRequest) request).copy());
        } else {
            DefaultHttpRequest copy = new DefaultHttpRequest(getProtocolVersion(), getMethod(), getUri());
            copy.headers().set(headers());
            return new EmptyHttpRequest(copy);
        }
    }

    @Override
    public FullHttpRequest copy(ByteBuf newContent) {
        if (request instanceof FullHttpRequest) {
            return new EmptyHttpRequest(((FullHttpRequest) request).copy(newContent));
        } else {
            FullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri()).copy(newContent);
            copy.headers().set(headers());
            return new EmptyHttpRequest(copy);
        }
    }

    @Override
    public FullHttpRequest retain(int increment) {
        ReferenceCountUtil.retain(message, increment);
        return this;
    }

    @Override
    public FullHttpRequest retain() {
        ReferenceCountUtil.retain(message);
        return this;
    }

    @Override
    public FullHttpRequest touch() {
        ReferenceCountUtil.touch(message);
        return this;
    }

    @Override
    public FullHttpRequest touch(Object hint) {
        ReferenceCountUtil.touch(message, hint);
        return this;
    }

    @Override
    public HttpHeaders trailingHeaders() {
        return new DefaultHttpHeaders();
    }

    @Override
    public FullHttpRequest duplicate() {
        if (request instanceof FullHttpRequest) {
            return ((FullHttpRequest) request).duplicate();
        } else {
            return this;
        }
    }

    @Override
    public ByteBuf content() {
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public int refCnt() {
        if (message instanceof ReferenceCounted) {
            return ((ReferenceCounted) message).refCnt();
        } else {
            return 1;
        }
    }

    @Override
    public boolean release() {
        return ReferenceCountUtil.release(message);
    }

    @Override
    public boolean release(int decrement) {
        return ReferenceCountUtil.release(message, decrement);
    }
}
