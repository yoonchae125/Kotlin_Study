package _04

import java.awt.event.MouseEvent
import java.awt.event.MouseAdapter
import java.awt.Window

//window.addMouseListener(
//	object: MouseAdapter(){
//		override fun mouseClicked(e: MouseEvent){}
//		override fun mouseEntered(e: MouseEvent){}
//	}
//)
fun counClicks(window:Window){
  var clickCount = 0
  window.addMouseListener(object: MouseAdapter(){
    override fun mouseClicked(e: MouseEvent){
      clickCount++
    }
  })
}