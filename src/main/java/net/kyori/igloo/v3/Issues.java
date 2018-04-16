/*
 * This file is part of igloo, licensed under the MIT License.
 *
 * Copyright (c) 2018 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.kyori.igloo.v3;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;

/**
 * A repository's issues.
 */
public interface Issues {
  /**
   * Gets an issue.
   *
   * @param number the issue number
   * @return the issue
   */
  @NonNull Issue get(final int number);

  /**
   * Creates a new issue.
   *
   * @param create the creation data
   * @param <C> the creation data type
   * @return the new issue
   * @throws IOException if an exception occurs while creating a new issue
   */
  <C extends Issue.AbstractCreate> @NonNull Issue create(final @NonNull C create) throws IOException;

  final class Impl implements Issues {
    private final Request request;

    Impl(final Request request) {
      this.request = request.path("issues");
    }

    @Override
    public @NonNull Issue get(final int id) {
      return new Issue.Impl(this.request, id);
    }

    @Override
    public <C extends Issue.AbstractCreate> @NonNull Issue create(final @NonNull C create) throws IOException {
      final Partial.Issue issue = this.request.post(create, Partial.Issue.class);
      return new Issue.Impl.Created(this.request, issue.number, issue.html_url);
    }
  }
}
