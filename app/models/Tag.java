package models;

import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import models.user.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dru on 10.09.15.
 */
public class Tag {

   private String id;

   private String name;

   public Tag(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public String getId() {
      return id;
   }

   protected void setId(String id) {
      this.id = id;
   }


   public static Tag fromDocument(ODocument doc) {
      if (doc == null) return null;

      Tag tag = new Tag(doc.field("name"));
      //non NPE solution
      tag.setId(String.valueOf(doc.getIdentity()));

      return tag;
   }

   public ODocument toDocument() {

      ODocument doc;

      if (getId() != null) {
         doc = new ODocument("PostTag", new ORecordId(getId()));
      } else
         doc = new ODocument("PostTag");

      doc.field("name", getName());

      return doc;
   }

   public static List<ODocument> toDocumentList(List<Tag> tags) {
      return tags.stream()
          .map(tag -> tag.toDocument())
          .collect(Collectors.toList());
   }

}
