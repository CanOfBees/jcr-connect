/** 
 *Copyright 2010 Northwestern University.
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 *    http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author Jonathan A. Smith
 * @version 03 [09] 2010
 */

package edu.northwestern.art.jcr_access.wrappers

import javax.jcr.Session

class JCRSession(val session: Session) {

  def root = new JCRNode(session.getRootNode)

  def \ (path: String) = root \ path
}

object JCRSession {

  implicit def asJCRSession(session: Session): JCRSession =
    new JCRSession(session)

  implicit def asSession(jcr_session: JCRSession): Session =
    jcr_session.session
}