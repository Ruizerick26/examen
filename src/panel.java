import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class panel {
    private JPanel panel1;
    private JTextField codigotxt;
    private JComboBox anio;
    private JComboBox mes;
    private JComboBox dia;
    private JTextField cedulatxt;
    private JTextField nombretxt;
    private JTextField apellidotxt;
    private JCheckBox rojoCheckBox;
    private JCheckBox verdeCheckBox;
    private JCheckBox ningunoCheckBox;
    private JRadioButton siRadioButton;
    private JRadioButton noRadioButton;
    private JComboBox signos;
    private JButton cargarButton;
    private JButton guardarButton;
    private JButton antes;
    private JButton siguiente;


    //variables de los txt
    private String nombre;
    private String apellido;
    private String codigo;
    private String cedula;
    private String casado;
    private String colorF;
    private String nacimiento;
    private String signo;
    private int i=0;

    String estudiantes[]=new String [3];

    public panel(){
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nombre = nombretxt.getText();
                apellido = apellidotxt.getText();
                cedula = cedulatxt.getText();
                codigo= codigotxt.getText();
                signo = (String) signos.getSelectedItem();
                nacimiento = ((String) anio.getSelectedItem()+ "/" + (String) mes.getSelectedItem()+ "/" + (String) dia.getSelectedItem());
                if(ningunoCheckBox.isSelected()){
                    colorF= "NINGUNO";
                }else{
                    if (rojoCheckBox.isSelected()) {
                        colorF = "ROJO " ;
                    }
                    if (verdeCheckBox.isSelected()) {
                        colorF = "VERDE ";
                    }
                    if (verdeCheckBox.isSelected() && rojoCheckBox.isSelected()){
                        colorF = "Rojo-Verde";
                    }
                }
                if(siRadioButton.isSelected()){
                    casado= "si";
                }
                if(noRadioButton.isSelected()){
                    casado="No";
                }
                estudiantes[i]=nombre + "," + apellido + "," + cedula + "," + codigo + "," +colorF + "," + casado + "," + nacimiento + "," +signo;
                System.out.println(estudiantes[i]);
                i=+1;
                nombretxt.setText("");
                apellidotxt.setText("");
                cedulatxt.setText("");
                codigotxt.setText("");

                try{
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("estudiantes.dat"));
                    objectOutputStream.writeObject(estudiantes);
                    objectOutputStream.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        cargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    FileInputStream dat = new FileInputStream("estudiantes.dat");
                    BufferedReader fil =  new BufferedReader(dat);
                    String linea;
                    while( (linea = fil.readline()) != null) {
                        String datostxt[] = linea.split(",");
                        System.out.println(linea);
                        if (datostxt.length == 8) {
                            nombre = datostxt[0];
                            apellido = datostxt[1];
                            cedula = datostxt[2];
                            codigo = datostxt[3];
                            colorF = datostxt[4];
                            casado = datostxt[5];
                            nacimiento = datostxt[6];
                            signo = datostxt[7];

                            nombretxt.setText(nombre);
                            apellidotxt.setText(apellido);
                            cedulatxt.setText(cedula);
                            codigotxt.setText(codigo);
                            if (colorF == "ROJO") {
                                rojoCheckBox.setEnabled(true);
                            }
                            if (colorF == "VERDE") {
                                verdeCheckBox.setEnabled(true);
                            }
                            if (colorF == "Rojo-Verde") {
                                rojoCheckBox.setEnabled(true);
                                verdeCheckBox.setEnabled(true);
                            }
                            if (colorF == "NINGUNO") {
                                ningunoCheckBox.setEnabled(true);
                            }
                            if (casado == "si") {
                                siRadioButton.setEnabled(true);
                            }
                            if (casado == "No") {
                                noRadioButton.setEnabled(true);
                            }
                            String nac[] = nacimiento.split("/");
                            anio.setSelectedItem(nac[0]);
                            mes.setSelectedItem(nac[1]);
                            dia.setSelectedItem(nac[2]);
                            signos.setSelectedItem(signo);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("panel");
        frame.setContentPane(new panel().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
