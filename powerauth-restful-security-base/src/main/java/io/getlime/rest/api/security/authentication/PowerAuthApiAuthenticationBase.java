/*
 * Copyright 2016 Lime - HighTech Solutions s.r.o.
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
package io.getlime.rest.api.security.authentication;

/**
 * Inteface for PowerAuth API authentication object used between intermediate server
 * application (such as mobile banking API) and core systems (such as banking core).
 *
 * @author Petr Dvorak
 *
 */
public interface PowerAuthApiAuthenticationBase {

    /**
     * Get user ID
     * @return User ID
     */
    String getUserId();

    /**
     * Set user ID
     * @param userId User ID
     */
    void setUserId(String userId);

    /**
     * Get activation ID
     * @return Activation ID
     */
    String getActivationId();

    /**
     * Set activation ID
     * @param activationId Activation ID
     */
    void setActivationId(String activationId);

}
