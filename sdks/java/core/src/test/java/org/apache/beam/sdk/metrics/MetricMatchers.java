/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.beam.sdk.metrics;

import java.util.Objects;
import org.apache.beam.sdk.metrics.MetricUpdates.MetricUpdate;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matchers for metrics.
 */
public class MetricMatchers {

  public static <T> Matcher<MetricUpdate<T>> metricUpdate(final String name, final T update) {
    return new TypeSafeMatcher<MetricUpdate<T>>() {
      @Override
      protected boolean matchesSafely(MetricUpdate<T> item) {
        return Objects.equals(name, item.getKey().metricName().name())
            && Objects.equals(update, item.getUpdate());
      }

      @Override
      public void describeTo(Description description) {
        description
            .appendText("MetricUpdate{name=").appendValue(name)
            .appendText(", update=").appendValue(update)
            .appendText("}");
      }
    };
  }

  public static <T> Matcher<MetricUpdate<T>> metricUpdate(
      final String namespace, final String name, final String step, final T update) {
    return new TypeSafeMatcher<MetricUpdate<T>>() {
      @Override
      protected boolean matchesSafely(MetricUpdate<T> item) {
        return Objects.equals(namespace, item.getKey().metricName().namespace())
            && Objects.equals(name, item.getKey().metricName().name())
            && Objects.equals(step, item.getKey().stepName())
            && Objects.equals(update, item.getUpdate());
      }

      @Override
      public void describeTo(Description description) {
        description
            .appendText("MetricUpdate{inNamespace=").appendValue(namespace)
            .appendText(", name=").appendValue(name)
            .appendText(", step=").appendValue(step)
            .appendText(", update=").appendValue(update)
            .appendText("}");
      }
    };
  }

  public static <T> Matcher<MetricResult<T>> metricResult(
      final String namespace, final String name, final String step,
      final T committed, final T attempted) {
    return new TypeSafeMatcher<MetricResult<T>>() {
      @Override
      protected boolean matchesSafely(MetricResult<T> item) {
        return Objects.equals(namespace, item.name().namespace())
            && Objects.equals(name, item.name().name())
            && Objects.equals(step, item.step())
            && Objects.equals(committed, item.committed())
            && Objects.equals(attempted, item.attempted());
      }

      @Override
      public void describeTo(Description description) {
        description
            .appendText("MetricResult{inNamespace=").appendValue(namespace)
            .appendText(", name=").appendValue(name)
            .appendText(", step=").appendValue(step)
            .appendText(", committed=").appendValue(committed)
            .appendText(", attempted=").appendValue(attempted)
            .appendText("}");
      }

      @Override
      protected void describeMismatchSafely(MetricResult<T> item, Description mismatchDescription) {
        mismatchDescription.appendText("MetricResult{");
        if (!Objects.equals(namespace, item.name().namespace())) {
          mismatchDescription
              .appendText("inNamespace: ").appendValue(namespace)
              .appendText(" != ").appendValue(item.name().namespace());
        }

        if (!Objects.equals(name, item.name().name())) {
          mismatchDescription
              .appendText("name: ").appendValue(name)
              .appendText(" != ").appendValue(item.name().name());
        }

        if (!Objects.equals(step, item.step())) {
          mismatchDescription
              .appendText("step: ").appendValue(step)
              .appendText(" != ").appendValue(item.step());
        }

        if (!Objects.equals(committed, item.committed())) {
          mismatchDescription
              .appendText("committed: ").appendValue(committed)
              .appendText(" != ").appendValue(item.committed());
        }

        if (!Objects.equals(attempted, item.attempted())) {
          mismatchDescription
              .appendText("attempted: ").appendValue(attempted)
              .appendText(" != ").appendValue(item.attempted());
        }
        mismatchDescription.appendText("}");
      }
    };
  }

}
