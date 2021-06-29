package com.cs.OSMProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JFrameUI extends JFrame {

    private static JScrollPane scrollPaneCoordinates = new JScrollPane();
    private static JLabel labelCoordinatesArrayList = new JLabel();
    private static JTextArea textAreaCoordinatesArrayList = new JTextArea();

    private static ArrayList<StorageData> storageDataArrayList = new ArrayList<StorageData>();

    private static ArrayList<Double> coordinates, center;

    public static void main(String[] args) {

        JFrame frame = new JFrame("OSMSBApp");

        JPanel panelName = new JPanel();
        panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));

        JPanel panelType = new JPanel();
        panelType.setLayout(new BoxLayout(panelType, BoxLayout.Y_AXIS));

        JPanel panelRadioButtons = new JPanel();
        panelRadioButtons.setLayout(new BoxLayout(panelRadioButtons, BoxLayout.X_AXIS));

        JPanel panelGeneral = new JPanel();
        panelGeneral.setLayout(new BoxLayout(panelGeneral, BoxLayout.Y_AXIS));

        JPanel panelCoordinates = new JPanel();
        panelCoordinates.setLayout(new BoxLayout(panelCoordinates, BoxLayout.Y_AXIS));

        JPanel panelCenterCoordinates = new JPanel();
        panelCenterCoordinates.setLayout(new BoxLayout(panelCenterCoordinates, BoxLayout.Y_AXIS));

        JLabel labelEnterName = new JLabel("Enter Name: ");
        JTextField textFiledName = new JTextField();

        JLabel labelChooseType = new JLabel("Choose Type: ");
        JRadioButton typeButtonCity = new JRadioButton("City");
        JRadioButton typeButtonCounty = new JRadioButton("County");
        JRadioButton typeButtonState = new JRadioButton("State");
        JRadioButton typeButtonCountry = new JRadioButton("Country");
        JRadioButton typeButtonPostalCode = new JRadioButton("Postal Code");

        ButtonGroup typeButtonGroup = new ButtonGroup();
        typeButtonGroup.add(typeButtonCity);
        typeButtonGroup.add(typeButtonCounty);
        typeButtonGroup.add(typeButtonState);
        typeButtonGroup.add(typeButtonCountry);
        typeButtonGroup.add(typeButtonPostalCode);

        //JLabel labelCoordinates = new JLabel("Coordinates of place: ");
        //JScrollPane scrollPaneCoordinates = new JScrollPane();
        //scrollPaneCoordinates.add(labelCoordinatesArrayList);
        scrollPaneCoordinates.add(textAreaCoordinatesArrayList);
        scrollPaneCoordinates.setPreferredSize(new Dimension(60, 100));
        scrollPaneCoordinates.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPaneCoordinates.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCoordinates.setViewportView(textAreaCoordinatesArrayList);


        JLabel labelCenterCoordinates = new JLabel();
        JLabel labelCenterCoordinatesArrayList = new JLabel();


        JButton ok = new JButton();
        ok.setText("Continue");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textFiledName.getText();
                String type = "";

                textAreaCoordinatesArrayList.setText("");
                labelCenterCoordinatesArrayList.setText("");

                for(int i = 0; i < typeButtonGroup.getButtonCount(); i++) {
                    if (typeButtonCity.isSelected())
                        type = "city";
                    if (typeButtonCounty.isSelected())
                        type = "county";
                    if (typeButtonState.isSelected())
                        type = "state";
                    if (typeButtonCountry.isSelected())
                        type = "country";
                    if (typeButtonPostalCode.isSelected())
                        type = "postalcode";
                }

                if (checkExistence(name, type) != -1) {
                    int index = checkExistence(name, type);

                    for (int i = 0; i < storageDataArrayList.get(index).coordinates.size(); i += 2) {
                        textAreaCoordinatesArrayList.append(storageDataArrayList.get(index).coordinates.get(i) + " :: " + storageDataArrayList.get(index).coordinates.get(i + 1) + "\n");
                    }

                    textAreaCoordinatesArrayList.append("\nData was loaded from cache");

                    labelCenterCoordinates.setText("Those are center coordinates: ");
                    labelCenterCoordinatesArrayList.setText(storageDataArrayList.get(index).center.get(1) + " :: " + storageDataArrayList.get(index).center.get(0));

                    SwingUtilities.updateComponentTreeUI(frame);

                } else {

                String urlRequest = RequestURLMaker.getURLJPanel(name, type);

                String geoJSONdata;

                geoJSONdata = OSMGetJSON.OSMGetJSONdata(urlRequest);
                coordinates = GeoJSONParser.ParseCoordinatesGeoJSON(geoJSONdata);

                if (GeoJSONParser.checkGeoJSON(geoJSONdata)) {
                    for (int i = 0; i < coordinates.size(); i += 2) {
                        textAreaCoordinatesArrayList.append(coordinates.get(i) + " :: " + coordinates.get(i + 1) + "\n");
                    }

                    center = Calculations.getCenter(coordinates);
                    labelCenterCoordinates.setText("Those are center coordinates: ");
                    labelCenterCoordinatesArrayList.setText(center.get(1) + " :: " + center.get(0));

                    saveToStorage(name, type, coordinates, center);

                    SwingUtilities.updateComponentTreeUI(frame);
                } else {
                    textAreaCoordinatesArrayList.append("Nothing found. Try again.");
                }
            }
            }
        });

        panelName.add(labelEnterName);
        panelName.add(textFiledName);
        panelGeneral.add(panelName);

        panelType.add(labelChooseType);
        panelType.add(panelRadioButtons);
        panelGeneral.add(panelType);

        panelCoordinates.add(scrollPaneCoordinates);
        panelGeneral.add(panelCoordinates);

        panelCenterCoordinates.add(labelCenterCoordinates);
        panelCenterCoordinates.add(labelCenterCoordinatesArrayList);
        panelGeneral.add(panelCenterCoordinates);

        panelRadioButtons.add(typeButtonCity);
        panelRadioButtons.add(typeButtonCounty);
        panelRadioButtons.add(typeButtonState);
        panelRadioButtons.add(typeButtonCountry);
        panelRadioButtons.add(typeButtonPostalCode);

        panelGeneral.add(ok);
        frame.add(panelGeneral);

        frame.setSize(600, 600);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static int checkExistence (String newRequestName, String newRequestType) {
        int index = -1;

        for (int i = 0; i < storageDataArrayList.size(); i++) {
            if (storageDataArrayList.get(i).getName().equals(newRequestName) && (storageDataArrayList.get(i).getType().equals(newRequestType))) {
                index = i;
                break;
            }
        }

        return index;
    }

    private static void saveToStorage (String name, String type, ArrayList<Double> coordinates, ArrayList<Double> center) {
        StorageData currentRequestData = new StorageData(name, type, coordinates, center);
        storageDataArrayList.add(currentRequestData);
    }

}
