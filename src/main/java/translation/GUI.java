package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Translator translator = new JSONTranslator();

            CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
            LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

            // initialize String[] countries
            String[] countries = new String[countryCodeConverter.getNumCountries()];

            int i = 0;
            for (String countryCode : translator.getCountryCodes()) {
                countries[i++] = countryCodeConverter.fromCountryCode(countryCode);
            }

            // language panel
            JPanel languagePanel = new JPanel();

            languagePanel.add(new JLabel("Language:"));

            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String languageCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(languageCodeConverter.fromLanguageCode(languageCode));
            }
            languagePanel.add(languageComboBox);

            // for displaying the result of translation
            JPanel resultPanel = new JPanel();

            resultPanel.add(new JLabel("Translation:"));
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            resultPanel.add(resultLabel);

            // country selection panel
            JPanel countryPanel = new JPanel();

            JList<String> countryList = new JList<>(countries);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane);

            // TODO add listener for languageComboBox when the user select the different language
            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    // NOTE: you should read from the source code of ComboBoxDemo.java
                    // HINT: languageComboBox.getSelectedItem().toString() is the current selected language
                }
            });

            // TODO add listener for countryList when the user clicks the countries scroll pane
            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    // NOTE: you should read from the source code of JListDemo.java
                    // HINT: countryList.getSelectedIndex() is the current index of selected country
                    // from String[] countries
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(resultPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
