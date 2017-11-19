/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.View;
import model.Library;
import view.view2;
/**
 *
 * @author Welcome
 */
public class controller {
    public view2 view;
    public Library lib;
    public int index;
    
    public controller(view2 view, Library lib) {
        this.view = view;
        this.lib = new Library();
        this.lib = lib;
        index = 0;
        view.setWord(lib.getWord(index).content);
        view.setMean(lib.getWord(index).translation);
//        view.actionNext(new nextWord());
//        view.actionPrev(new prevWord());
        view.changeShowMean(new changeShowMean());
        view.showMean(true);
        view.checkShowMean(true);
    }
    
    class changeShowMean implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            if (view.isShowMean()){
                view.showMean(true);
            } else {
                view.showMean(false);
            }
        }
        
    }
}
