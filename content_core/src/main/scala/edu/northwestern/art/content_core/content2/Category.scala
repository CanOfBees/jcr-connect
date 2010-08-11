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
 * @version 10 [08] 2010
 */

package edu.northwestern.art.content_core.content2

import org.json.JSONObject
import edu.northwestern.art.content_core.properties.JSONSerializable

class Category(
    val taxonomy: Taxonomy,
    val name: String,
    val description: String,
    val parent: Option[Category],
    val children: Array[Category]
  )
  extends JSONSerializable {

  /**
   * Applies a visitor to this object.
   */

  def accept[T](visitor: ContentVisitor[T]): T =
    visitor.visitCategory(this)

  /**
   * Returns a JSON representation of this Metadata.
   */

  override def toJSON: JSONObject = accept(JSONVisitor)

}