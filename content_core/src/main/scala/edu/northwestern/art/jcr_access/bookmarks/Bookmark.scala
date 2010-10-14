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
 * @version 11 [10] 2010
 */

package edu.northwestern.art.jcr_access.bookmarks

import edu.northwestern.art.content_core.utilities.Storage
import java.util.ArrayList
import javax.persistence._
import scala.collection.JavaConversions._

import edu.northwestern.art.content_core.properties.{Properties, JSONSerializable}

@Entity
class Bookmark(init_user: User, init_workspace: String,
    init_path: String) extends JSONSerializable {

  def this() = this(null, null, null)

  @Id @GeneratedValue
  var id: Int = 0

  @ManyToOne
  var user: User = init_user

  var workspace: String = init_workspace

  var path: String = init_path

  var categories: java.util.List[Category] = new ArrayList

  def categoryNames = (categories.toList map (_.name))

  def removeCategories {
    for (category <- categories.toList) {
      category.removedFrom(this)
      categories.remove(category)
    }
  }

  def categoryNames_= (names: Iterable[String]) {
    removeCategories
    val new_categories = new ArrayList[Category]
    for (name <- names) {
      val new_category = Category.findOrCreate(user, name)
      new_category.addedTo(this)
      new_categories.add(new_category)
    }
    categories = new_categories
  }

  def toJSON = Properties("workspace" -> workspace, "path" -> path,
      "categories" -> (categories.toList map (_.name))).toJSON
}

object Bookmark extends Storage[Bookmark] {

  /**
   * Creates a new Bookmark with a specified user, workspace, and path.
   */

  def create(user: User, workspace: String, path: String): Bookmark = {
    val new_bookmark = new Bookmark(user, workspace, path)
    persist(new_bookmark)
    user.bookmarkAdded(new_bookmark)
    new_bookmark
  }

  def find(user: User, workspace: String, path: String): Option[Bookmark] = {
    val query = Bookmark.manager.createQuery(
      "SELECT b FROM Bookmark b WHERE " +
         "b.user = :user AND b.workspace = :workspace AND b.path = :path")
    query.setParameter("user", user)
    query.setParameter("workspace", workspace)
    query.setParameter("path", path)
    try {
      Some(query.getSingleResult.asInstanceOf[Bookmark])
    }
    catch {
      case except: NoResultException =>
        None
    }
  }

  def findOrCreate(user: User, workspace: String, path: String) = {
    find(user, workspace, path) match {
      case Some(bookmark) =>
        bookmark
      case None =>
        create(user, workspace, path)
    }
  }
}