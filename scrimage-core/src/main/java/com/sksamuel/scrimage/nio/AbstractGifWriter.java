package com.sksamuel.scrimage.nio;

import javax.imageio.metadata.IIOMetadataNode;
import java.time.Duration;

abstract class AbstractGifWriter {

   /**
    * Returns an existing child node, or creates and returns a new child node (if
    * the requested node does not exist).
    *
    * @param rootNode the <tt>IIOMetadataNode</tt> to search for the child node.
    * @param nodeName the name of the child node.
    * @return the child node, if found or a new node created with the given name.
    */
   protected IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
      for (int i = 0; i < rootNode.getLength(); i++) {
         IIOMetadataNode node = (IIOMetadataNode) rootNode.item(i);
         if (node.getNodeName().equalsIgnoreCase(nodeName)) {
            return node;
         }
      }
      IIOMetadataNode node = new IIOMetadataNode(nodeName);
      rootNode.appendChild(node);
      return node;
   }

   protected void populateGraphicsControlNode(IIOMetadataNode root, Duration frameDelay) {
      IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
      graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
      graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
      graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
      graphicsControlExtensionNode.setAttribute("delayTime", (frameDelay.toMillis() / 10) + "");
      graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");
   }

   protected void populateCommentsNode(IIOMetadataNode root) {
      IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
      commentsNode.setAttribute("CommentExtension", "Created by Scrimage");
   }

   protected void populateApplicationExtensions(IIOMetadataNode root, boolean infiniteLoop) {
      IIOMetadataNode appEntensionsNode = getNode(root, "ApplicationExtensions");
      IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

      child.setAttribute("applicationID", "NETSCAPE");
      child.setAttribute("authenticationCode", "2.0");

      int loop = infiniteLoop ? 0 : 1;
      child.setUserObject(new byte[]{0x1, (byte) (loop & 0xFF), (byte) (0)});
      appEntensionsNode.appendChild(child);
   }
}