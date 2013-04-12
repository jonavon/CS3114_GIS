package edu.vt.jowilcox.cs3114.p4.prquadtree;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import edu.vt.jowilcox.cs3114.p4.point.Point;
import edu.vt.jowilcox.cs3114.p4.prquadtree.prQuadtree.prQuadInternal;
import edu.vt.jowilcox.cs3114.p4.prquadtree.prQuadtree.prQuadLeaf;
import edu.vt.jowilcox.cs3114.p4.prquadtree.prQuadtree.prQuadNode;

public class Lewis {

   int xMin = 0, 
       xMax = 0, 
       yMin = 0, 
       yMax = 0;
   Vector<Point> data = null;
   Random        source;
   String        pad;
   
   public Lewis(int xMin, int xMax, int yMin, int yMax, Vector<Point> data, Random source, boolean profMode) {
      
     this.xMin = xMin;
     this.xMax = xMax;
     this.yMin = yMin;
     this.yMax = yMax;
     this.data = data;
     this.source = source;
     pad = new String("---");
   }
   
   public void checkTreeInitialization() {
         
      String Notes = new String();
      Notes = Notes + " checkTreeInitialization results:\n";
      Notes = Notes + "   Creating a new prQuadtree with world boundaries:\n";
      Notes = Notes + "     xMin:  " + xMin + "\n";
      Notes = Notes + "     xMax:  " + xMax + "\n";
      Notes = Notes + "     yMin:  " + yMin + "\n";
      Notes = Notes + "     yMax:  " + yMax + "\n";
      
      prQuadtree<Point> Tree = new prQuadtree<Point>(xMin, xMax, yMin, yMax);
      if ( Tree.root != null ) {
         Notes = Notes + "   Bummer:  prQuadtree root was NOT null.\n";
      }
      else {
         Notes = Notes + "   prQuadtree root was OK.\n";
      }
      if ( Tree.xMin != xMin ) {
         Notes = Notes + "   Bummer:  tree has xMin as " + Tree.xMin + ".\n";
      }
      else {
         Notes = Notes + "   prQuadtree xMin was OK.\n";
      }
      if ( Tree.xMax != xMax ) {
         Notes = Notes + "   Bummer:  tree has xMax as " + Tree.xMax + ".\n";
      }
      else {
         Notes = Notes + "   prQuadtree xMax was OK.\n";
      }
      if ( Tree.yMin != yMin ) {
         Notes = Notes + "   Bummer:  tree has yMin as " + Tree.yMin + ".\n";
      }
      else {
         Notes = Notes + "   prQuadtree yMin was OK.\n";
      }
      if ( Tree.yMax != yMax ) {
         Notes = Notes + "   Bummer:  tree has yMax as " + Tree.yMax + ".\n";
      }
      else {
         Notes = Notes + "   prQuadtree yMax was OK.\n";
      }

      try {
         String logName = "TestTreeInitialization.txt";
         FileWriter Log = new FileWriter(logName);
         Log.write(Notes);
         Log.close();
      }
      catch ( IOException e ) {
         System.out.println("Error writing notes to log file in Lewis.checkTreeInitialization().");
      }
   }
   
    public void checkInsertion() throws IOException {
       
       FileWriter Log = null;
        try {
            String logName = "TestInsertion.txt";
            Log = new FileWriter(logName);
        }
        catch ( IOException e ) {
           System.out.println("Error writing notes to log file in Lewis.checkInsertion().");
        }
       String Notes = new String();
       prQuadtree<Point> Tree = new prQuadtree<Point>(xMin, xMax, yMin, yMax);
       
       Notes += " checkInsertion() results:\n";
       
       for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
          Notes += "   Inserting value: " + data.elementAt(dataIdx) + "\n";
          boolean success = false;
          try {
             success = Tree.insert(data.elementAt(dataIdx));
          }
          catch ( Exception e ) {
              Notes += "   Caught an exception while inserting value:\n";
              Notes += "     " + e.getMessage() + "\n";
              Notes += "   Aborting insertion test.\n";
              Log.write(Notes);
              Log.close();
              return;
          }

          try {
             if ( !success ) {
                Notes += "   Error: insert() returned false.\n";
             }
             else {
                Notes += "   insert() returned true.\n";
             }
             Notes += "   Resulting tree:\n";
             Log.write(Notes);
             Notes = "";
             printTree(Log, Tree, 1);
          }
          catch ( IOException e ) {
             System.out.println("Error writing notes to log file in Lewis.checkInsertion().");
          }
       }
       
       try {
           int dataIdx = Math.abs(source.nextInt()) % data.size();
           Notes += "   Now trying to insert a duplicate entry: " + data.elementAt(dataIdx) + "\n";
           boolean success;
           try {
              success = Tree.insert( data.elementAt(dataIdx) );
           }
          catch ( Exception e ) {
              Notes += "   Caught an exception while inserting value:\n";
              Notes += "     " + e.getMessage() + "\n";
              Notes += "   Aborting insertion test.\n";
              Log.write(Notes);
              Log.close();
              return;
          }
         if ( success ) {
            Notes += "   Error: insert() returned true.\n";
         }
         else {
            Notes += "   insert() returned false.\n";
         }
         Notes += "   Resulting tree:\n";
         Log.write(Notes);
         Notes = "";
         printTree(Log, Tree, 1);
       }
       catch ( Exception e ) {
          Notes += "   Caught an exception during insertion.\n";
          Notes += "     " + e.getMessage() + "\n";
       }
       
        try {
            Log.write(Notes);
            Log.close();
        }
        catch ( IOException e ) {
           System.out.println("Error writing notes to log file in Lewis.checkInsertion().");
        }
    }
    
    public void checkDeletion() throws IOException {
       
       FileWriter Log = null;
        try {
            String logName = "TestDeletion.txt";
            Log = new FileWriter(logName);
        }
        catch ( IOException e ) {
           System.out.println("Error writing notes to log file in Lewis.checkDeletion().");
        }
       String Notes = new String();
      prQuadtree<Point> Tree = new prQuadtree<Point>(xMin, xMax, yMin, yMax);
       
       Notes += " checkDeletion() results:\n";
       Notes += "     Building a tree for testing...(if checkInsertion() failed this should fail also)\n";

       try {
          for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
             Notes += "       Inserting value: " + data.elementAt(dataIdx) + "\n";
             @SuppressWarnings("unused")
			boolean success = Tree.insert(data.elementAt(dataIdx));
          }
       } catch ( Exception e ) {
          Notes += "   Caught an exception while building tree:\n";
          Notes += "     " + e.getMessage() + "\n";
          Notes += "   Aborting deletion test.\n";
          Log.write(Notes);
          Log.close();
          return;
       }
       
       try {
          Notes += "     Resulting tree:\n";
          Log.write(Notes);
          Notes = "";
          printTree(Log, Tree, 1);
       }
       catch ( IOException e ) {
          System.out.println("Error writing notes to log file in Lewis.checkDeletion().");
       }
       catch ( Exception e ) {
          Notes += "   Caught an exception while displaying tree.\n";
          Notes += "     " + e.getMessage() + "\n";
          Notes += "   Aborting deletion test.\n";
          Log.write(Notes);
          Log.close();
          return;
       }
      
       try {
          for (int dataIdx = 0; dataIdx < data.size(); dataIdx++) {
             Notes += "    Deleting value: " + data.elementAt(dataIdx) + "\n";
             Point p = data.elementAt(dataIdx);
             boolean success = Tree.delete( new Point(p.getX(), p.getY()));

             try {
                if ( !success ) {
                   Notes += "   Bummer: delete() returned false.\n";
                }
                else {
                   Notes += "   delete() returned true.\n";
                }
                Notes += "   Resulting tree:\n";
                Log.write(Notes);
                Notes = "";
                printTree(Log, Tree, 4); 
             }
             catch ( IOException e ) {
                System.out.println("Error writing notes to log file in Lewis.checkDeletion().");
             }
          }
       }
       catch ( Exception e ) {
          Notes += "   Caught an exception while performing deletion:\n";
          Notes += "     " + e.getMessage() + "\n";
          Notes += "   Aborting deletion test.\n";
          Log.write(Notes);
          Log.close();
          return;
       }
        try {
            Log.write(Notes);
            Log.close();
        }
        catch ( IOException e ) {
           System.out.println("Error writing notes to log file in Lewis.checkDeletion().");
        }
       
    }

    public void printTree(FileWriter Out, prQuadtree<Point> Tree, int ptsPerDataItem) {
       try {
           if ( Tree.root == null )
              Out.write( "  Empty tree.\n" );
           else
              printTreeHelper(Out,  Tree.root, "", ptsPerDataItem);
       }
       catch ( IOException e ) {
          return;
       }
     }

   public void printTreeHelper(FileWriter Out, @SuppressWarnings("rawtypes") prQuadNode sRoot, String Padding, int ptsPerDataItem) {

      try {
         // Check for empty leaf
         if ( sRoot == null ) {
            Out.write( " " + Padding + "*\n");
            return;
         }
         // Check for and process SW and SE subtrees
         if ( sRoot.getClass().getName().equals("Minor.P3.DS.prQuadtree$prQuadInternal") ) {
            @SuppressWarnings("rawtypes")
			prQuadInternal p = (prQuadInternal) sRoot;
            printTreeHelper(Out, p.SW, Padding + pad, ptsPerDataItem);
            printTreeHelper(Out, p.SE, Padding + pad, ptsPerDataItem);
         }
 
         // Determine if at leaf or internal and display accordingly
         if ( sRoot.getClass().getName().equals("Minor.P3.DS.prQuadtree$prQuadLeaf") ) {
            @SuppressWarnings("rawtypes")
			prQuadLeaf p = (prQuadLeaf) sRoot;
            for (int pos = 0; pos < p.Elements.size(); pos++) {
               Out.write( Padding + p.Elements.get(pos) + "\n" );
            }
         }
         else if ( sRoot.getClass().getName().equals("Minor.P3.DS.prQuadtree$prQuadInternal") )
            Out.write( Padding + "@\n" );
         else
            Out.write( sRoot.getClass().getName() + "#\n");

         // Check for and process NE and NW subtrees
         if ( sRoot.getClass().getName().equals("Minor.P3.DS.prQuadtree$prQuadInternal") ) {
            @SuppressWarnings("rawtypes")
			prQuadInternal p = (prQuadInternal) sRoot;
            printTreeHelper(Out, p.NE, Padding + pad, ptsPerDataItem);
            printTreeHelper(Out, p.NW, Padding + pad, ptsPerDataItem);
         }
      }
      catch ( IOException e ) {
         return;
      }
   }
}
