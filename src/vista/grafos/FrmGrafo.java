/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.grafos;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.view.mxGraph;
import controlador.ed.grafo.Adycencia;
import controlador.ed.grafo.Grafo;
import controlador.ed.grafo.GrafoEtiquetadoD;
import controlador.ed.lista.ListaEnlazada;
import java.awt.Dimension;

/**
 *
 * @author darkangel
 */
public class FrmGrafo extends javax.swing.JDialog {

    private Grafo grafo;

    /**
     * Creates new form FrmGrafo
     */
    public FrmGrafo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public FrmGrafo(java.awt.Frame parent, boolean modal, Grafo grafo) {
        super(parent, modal);
        initComponents();
        this.grafo = grafo;
        System.out.println(this.grafo.getClass().getSimpleName());
        if (this.grafo.getClass().getSimpleName().equalsIgnoreCase("GrafoEtiquetadoND")
                || this.grafo.getClass().getSimpleName().equalsIgnoreCase("GrafoEtiquetadoD")) {
            cargarDatos(2);
        } else {
            cargarDatos(1);
        }
    }

    /**
     * Cargas datos del grafo
     *
     * @param tipo 1 es grafo normal y si es 2 grafo etiquetado
     */
    private void cargarDatos(Integer tipo) {
        mxGraph graph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setSize(new Dimension(1200, 1200));
        getContentPane().add(graphComponent);
        ListaEnlazada<Object> pintados = new ListaEnlazada<>();
        Object parent = graph.getDefaultParent();
        if (tipo == 1) {
            //TODO
            //implementar el grafo normal
        } else {
            for (int i = 1; i <= grafo.numVertices(); i++) {
                GrafoEtiquetadoD ged = (GrafoEtiquetadoD) grafo;
                Object start = graph.insertVertex(parent, ged.getEtiqueta(i).toString(), ged.getEtiqueta(i).toString(), 100, 150, 30, 30);
                pintados.insertar(start);
            }
            //PINTAR ADYACENCIAS
            Object[] pintadosArray = pintados.toArray();
            System.out.println(pintadosArray.length+"  "+pintados.size());
            for (int i = 1; i <= grafo.numVertices(); i++) {
                try {
                    Adycencia[] lista = grafo.adycentes(i).toArray();
                    //ListaEnlazada<Adycencia> lista = grafo.adycentes(i);
                    Object start = pintadosArray[i-1];
                    for (int j = 0; j < lista.length; j++) {
                        Adycencia a = lista[j];
                        Object dest = pintadosArray[a.getDestino()-1];
                        graph.insertEdge(parent, null, String.valueOf(a.getPeso()), start, dest);
                    }
                } catch (Exception e) {
                }
            }
        }
        morphGraph(graph, graphComponent);
        new mxCircleLayout(graph).execute(graph.getDefaultParent());
    }

    private void morphGraph(mxGraph graph, mxGraphComponent graphComponent) {
        mxIGraphLayout layout = new mxFastOrganicLayout(graph);
        graph.getModel().beginUpdate();
        try {
            layout.execute(graph.getDefaultParent());
        } catch (Exception e) {
        } finally {
            mxMorphing morph = new mxMorphing(graphComponent, 20, 1.5, 20);
            morph.addListener(mxEvent.DONE, new mxEventSource.mxIEventListener() {
                @Override
                public void invoke(Object o, mxEventObject eo) {
                    graph.getModel().endUpdate();
                }
            });
            morph.startAnimation();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 912, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrafo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmGrafo dialog = new FrmGrafo(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
