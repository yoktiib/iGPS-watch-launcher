/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.networkevents.library.utils;

import com.github.pwittchen.networkevents.library.event.ConnectivityChanged;
import com.squareup.otto.Subscribe;

import java.util.List;

public final class TestUtils {
  public static Object getConnectivityEventCatcher(final List<ConnectivityChanged> events) {
    return new Object() {
      @SuppressWarnings("unused") @Subscribe
      public void onConnectivityChanged(ConnectivityChanged event) {
        events.add(event);
      }
    };
  }
}
