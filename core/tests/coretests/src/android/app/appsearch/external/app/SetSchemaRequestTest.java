/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.app.appsearch;

import static com.google.common.truth.Truth.assertThat;

import static org.testng.Assert.expectThrows;

import org.junit.Test;

public class SetSchemaRequestTest {

    @Test
    public void testInvalidSchemaReferences() {
        IllegalArgumentException expected =
                expectThrows(
                        IllegalArgumentException.class,
                        () ->
                                new SetSchemaRequest.Builder()
                                        .setSchemaTypeVisibilityForSystemUi(false, "InvalidSchema")
                                        .build());
        assertThat(expected).hasMessageThat().contains("referenced, but were not added");
    }

    @Test
    public void testSchemaTypeVisibilityForSystemUi_Visible() {
        AppSearchSchema schema = new AppSearchSchema.Builder("Schema").build();

        // By default, the schema is visible.
        SetSchemaRequest request = new SetSchemaRequest.Builder().addSchema(schema).build();
        assertThat(request.getSchemasNotPlatformSurfaceable()).isEmpty();

        request =
                new SetSchemaRequest.Builder()
                        .addSchema(schema)
                        .setSchemaTypeVisibilityForSystemUi(true, "Schema")
                        .build();
        assertThat(request.getSchemasNotPlatformSurfaceable()).isEmpty();
    }

    @Test
    public void testSchemaTypeVisibilityForSystemUi_NotVisible() {
        AppSearchSchema schema = new AppSearchSchema.Builder("Schema").build();
        SetSchemaRequest request =
                new SetSchemaRequest.Builder()
                        .addSchema(schema)
                        .setSchemaTypeVisibilityForSystemUi(false, "Schema")
                        .build();
        assertThat(request.getSchemasNotPlatformSurfaceable()).containsExactly("Schema");
    }
}
