package com.emamagic.data_android.interceptor.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private Callback callback;

    public DownloadProgressResponseBody(Callback callback) {
        this.callback = callback;
    }

    public void setResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            private float progress = 0;

            @Override
            public long read(@NotNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                float progress = ((float) totalBytesRead / responseBody.contentLength());
                if (bytesRead != -1 && progress != this.progress) {
                    this.progress = progress;
                    callback.onProgress(progress);
                }
                return bytesRead;
            }
        };

    }

    public interface Callback {
        void onProgress(float progress);
    }
}
