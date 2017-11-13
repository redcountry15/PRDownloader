/*
 *    Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.downloader.internal;

import com.downloader.Error;
import com.downloader.Priority;
import com.downloader.Response;
import com.downloader.request.DownloadRequest;

/**
 * Created by amitshekhar on 13/11/17.
 */

public class DownloadRunnable implements Runnable {

    public final Priority priority;
    public final int sequence;
    public final DownloadRequest request;

    public DownloadRunnable(DownloadRequest request) {
        this.request = request;
        this.priority = request.getPriority();
        this.sequence = request.getSequenceNumber();
    }

    @Override
    public void run() {
        Fetcher fetcher = Fetcher.create(request);
        Response response = fetcher.fetch();
        if (response.isSuccessful()) {
            request.deliverSuccess();
        } else if (response.isPaused()) {
            request.deliverPauseEvent();
        } else {
            request.deliverError(new Error());
        }
    }

}
