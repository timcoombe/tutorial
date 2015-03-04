package util

class BreadCrumb {

  val breadCrumbs = scala.collection.mutable.MutableList[BreadCrumbLink]()

  /*def getLinks() : String = {
    
    var buffer = new StringBuilder
    
    breadCrumbs.foreach{ breadCrumb =>
      
      val text = breadCrumb.text
      val link = breadCrumb.link

      buffer.append(s"<a href='$link'>$text</a>&nbsp;" )
      
      
    }
    buffer.toString()
  }*/
  
  def addLink(text: String, link: String): Unit ={  
    breadCrumbs+=new BreadCrumbLink(text,link)
  }

}

case class BreadCrumbLink(text: String, link: String){}