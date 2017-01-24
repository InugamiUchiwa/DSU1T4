/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tarea;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author melee_000
 */
public class Control implements ActionListener{
    private MergeSort merge = new MergeSort();
    private LectorValidador ordTxt = new LectorValidador();
    private ArrayList lista;
    private vistaEditor ventana;
    private ventanaCambio cambio;
    private ArrayList cabecera = new ArrayList();
    private int columnas, columnaOrd, filaTabla, columnaTabla;
    private String separador;
    
    public Control(String[] argumentos){
        boolean band = false;
        this.separador = argumentos[1];
        this.columnaOrd = Integer.parseInt(argumentos[2]);
        this.lista = ordTxt.leerTexto(argumentos);
        if(lista != null){
            band = ordTxt.validarTexto(lista);
        }
        if(band == true){
            columnas = Integer.parseInt(lista.get(0).toString());
            lista.remove(0);
            for(int i=0; i<columnas; i++){
                cabecera.add(lista.get(0).toString());
                lista.remove(0);
            }
            //aquí se crea la instancia para la interfaz gráfica y los eventos.
            ventana = new vistaEditor();
            cambio = new ventanaCambio();
            ventana.botonModificar.addActionListener(this);
            ventana.botonListo.addActionListener(this);
            ventana.tablaInfo.setFocusable(false);
            cambio.botonAceptar.addActionListener(this);
            cambio.botonCancelar.addActionListener(this);
            llenarTabla();
            ventana.setVisible(band);  
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(ventana.botonListo == e.getSource()){
            ordTxt.sobreescribirArchivo(lista);
            lista = merge.ordenaMerge(lista, separador, columnaOrd);
            ordTxt.imprimirArchivo(lista);
            ventana.dispose();
            cambio.dispose();
            
        }else if(ventana.botonModificar == e.getSource()){
            columnaTabla = ventana.tablaInfo.getSelectedColumn();
            filaTabla = ventana.tablaInfo.getSelectedRow();
            JTableHeader th = ventana.tablaInfo.getTableHeader();
            TableColumnModel tcm = th.getColumnModel();
            TableColumn tcmn = tcm.getColumn(columnaTabla);
            cambio.etiquetaColumna.setText(tcmn.getHeaderValue().toString()+":");
            cambio.areaTexto.setText(ventana.tablaInfo.getValueAt(filaTabla, columnaTabla).toString());
            cambio.setVisible(true);
            
        }else if(cambio.botonAceptar == e.getSource()){
            cambio.botonAceptar.setEnabled(false);
            ventana.tablaInfo.setValueAt(cambio.areaTexto.getText(), filaTabla, columnaTabla);
            cambio.dispose();
            String nuevoRegistro="";
            for(int i=0; i<columnas; i++){
                if(i<columnas-1){
                nuevoRegistro = nuevoRegistro+ventana.tablaInfo.getValueAt(filaTabla, i)+",";
                }else{
                   nuevoRegistro = nuevoRegistro+ventana.tablaInfo.getValueAt(filaTabla, i); 
                }
            }
            lista.remove(filaTabla);
            lista.add(filaTabla, nuevoRegistro);
    
        }else if(cambio.botonCancelar == e.getSource()){
            cambio.botonAceptar.setEnabled(false);
            cambio.dispose();
        }
    }
    
    
    
    public void llenarTabla(){
        String[] cab = new String[cabecera.size()];
        for(int i=0; i<cabecera.size(); i++){
            cab[i]=cabecera.get(i).toString();
        }
        DefaultTableModel dtm = new DefaultTableModel(null,cab){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        ventana.tablaInfo.setModel(dtm);
        for(int i=0; i<lista.size(); i++){
            int cont =0;
            StringTokenizer tokens = new StringTokenizer(lista.get(i).toString(), separador);
            while(tokens.hasMoreTokens()){
                cab[cont]=tokens.nextToken();
                cont++;
            }
            dtm.addRow(cab);
            
        }
    }
    
     public static void main(String[] args) {
        if(args[0].contains(".txt") && args.length==3){
            int cont = 0;
            for(int i=0; i<args[1].length(); i++){
                if(Character.isDigit(args[1].charAt(i)) || Character.isAlphabetic(args[1].charAt(i)))  {
                    cont++;
                }                  
            }
            if(cont == 0 && Integer.parseInt(args[2])>0){
                Control control = new Control(args); 
            }else{
                System.out.println("parametros incorrectos");
            }
            
        }else{
            System.out.println("parametros incorrectos");
        }
    }
}
