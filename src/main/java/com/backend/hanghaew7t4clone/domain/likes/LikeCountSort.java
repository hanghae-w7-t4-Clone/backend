package com.backend.hanghaew7t4clone.domain.likes;

import com.backend.hanghaew7t4clone.domain.comment.Comment;

import java.util.Comparator;

public class LikeCountSort implements Comparator<Comment> {
   int ret = 0;

   @Override
   public int compare(Comment c1, Comment c2) {
      if (c1.getLikeCount() < c2.getLikeCount()) {
         ret = 1;
      } else if (c1.getLikeCount() > c2.getLikeCount()) {
         ret = -1;
      } else {
         if (c1.getId() > c2.getId()) {
            ret = 1;
         } else {
            ret = -1;
         }
      }
return ret;
   }
}
