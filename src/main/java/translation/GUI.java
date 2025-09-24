package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class GUI {

    public static void main(String[] args) {
            Translator translator = new JSONTranslator("sample.json");
            LanguageCodeConverter Lconverter = new LanguageCodeConverter("language-codes.txt");
            CountryCodeConverter Cconverter = new CountryCodeConverter("country-codes.txt");

            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new GridLayout(0, 2));
            countryPanel.add(new JLabel("Country:"), 0);
            String[] items = new String[translator.getCountryCodes().size()];
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = Cconverter.fromCountryCode(countryCode);
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> countryList = new JList<>(items);
            countryList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(countryList);
            countryPanel.add(scrollPane, 1);

            JPanel languagePanel = new JPanel();
            languagePanel.add(new JLabel("Language:"));
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String countryCode : translator.getLanguageCodes()) {
                languageComboBox.addItem(Lconverter.fromLanguageCode(countryCode));
            }
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            Runnable updateTranslation = () -> {
                String selectedLanguage = (String) languageComboBox.getSelectedItem();
                String selectedCountry = countryList.getSelectedValue();

                if (selectedLanguage == null || selectedCountry == null) {
                    resultLabel.setText("");
                    return;
                }

                String countryCode = Cconverter.fromCountry(selectedCountry);
                String languageCode = Lconverter.fromLanguage(selectedLanguage);
                String result = translator.translate(countryCode, languageCode);

                if (result == null) {
                    result = "no translation found!";
                }
                resultLabel.setText(result);
            };

            languageComboBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateTranslation.run();
                }
            });

            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    updateTranslation.run();
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(countryPanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
    }
}
