/*
 * Copyright (c) 2014, Cloudera, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */

package com.cloudera.oryx.app.serving.als;

import java.util.Objects;

import com.cloudera.oryx.common.math.VectorMath;

/**
 * Computes the dot product of a target vector and other vectors.
 */
public final class DotsFunction implements CosineDistanceSensitiveFunction {

  private final float[] userFeaturesVector;

  public DotsFunction(float[] userVector) {
    Objects.requireNonNull(userVector);
    this.userFeaturesVector = userVector;
  }

  public DotsFunction(float[][] userFeaturesVectors) {
    float[] userFeaturesVector = new float[userFeaturesVectors[0].length];
    for (float[] vec : userFeaturesVectors) {
      for (int i = 0; i < vec.length; i++) {
        userFeaturesVector[i] += vec[i];
      }
    }
    for (int i = 0; i < userFeaturesVector.length; i++) {
      userFeaturesVector[i] /= userFeaturesVectors.length;
    }
    this.userFeaturesVector = userFeaturesVector;
  }

  @Override
  public double applyAsDouble(float[] itemVector) {
    return VectorMath.dot(userFeaturesVector, itemVector);
  }

  @Override
  public float[] getTargetVector() {
    return userFeaturesVector;
  }

}
