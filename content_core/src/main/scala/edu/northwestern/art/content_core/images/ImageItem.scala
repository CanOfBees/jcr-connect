/**
 *  Copyright 2010 Northwestern University.
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
 * @version 14 July 2010
 */

package edu.northwestern.art.content_core.images

import java.util.{Date, ArrayList}

import scala.collection.JavaConversions._

import javax.persistence.{OneToMany, Entity}

import edu.northwestern.art.content_core.properties.Properties
import edu.northwestern.art.content_core.content.{Category, Item, Metadata}
import edu.northwestern.art.content_core.utilities.Storage

@Entity
class ImageItem extends Item {

  @OneToMany(mappedBy="item")
  var sources: java.util.Map[String, ImageSource] =
      new java.util.HashMap[String, ImageSource]

  override def toJSON = Properties(
      "name"      -> name,
      "metadata"  -> metadata,
      "modified"  -> modified,
      "sources"   -> sources).toJSON
}

object ImageItem extends Storage[ImageItem] {

  def initialize(item: ImageItem, name: String, metadata: Metadata,
      modified: Date, categories: Iterable[Category],
      sources: Map[String, ImageSource]): ImageItem = {
    Item.initialize(item, name, metadata, modified, categories)
    item.sources = new java.util.HashMap(sources)
    item
  }

  def create(name: String, metadata: Metadata, modified: Date = new Date,
      categories: Iterable[Category] = List(),
      sources: Map[String, ImageSource] = Map()) = {
    val item = new ImageItem
    persist(item)
    initialize(item, name, metadata, modified, categories, sources)
  }

  def apply(name: String, metadata: Metadata, modified: Date = new Date,
      categories: Iterable[Category] = List(),
      sources: Map[String, ImageSource] = Map()) =
    initialize(new ImageItem, name, metadata, modified,
      categories, sources)
}

