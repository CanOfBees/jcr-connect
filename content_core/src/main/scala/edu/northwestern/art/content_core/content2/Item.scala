/** 
 * Copyright 2010 Northwestern University.
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
 * @version 10 August 2010
 */

package edu.northwestern.art.content_core.content2

import java.util.Date
import edu.northwestern.art.content_core.properties.JSONSerializable
import org.json.JSONObject

class Item(
    val name: String,
    val metadata: Metadata,
    val categories: List[Category] = List(),
    val created: Date              = new Date,
    val modified: Date             = new Date
  )
  extends JSONSerializable {

  /**
   * Applies a visitor to this object.
   */

  def accept[T](visitor: ContentVisitor[T]): T =
    visitor.visitItem(this)

  /**
   * Returns a JSON representation of this Metadata.
   */

  override def toJSON: JSONObject = accept(JSONVisitor)

}