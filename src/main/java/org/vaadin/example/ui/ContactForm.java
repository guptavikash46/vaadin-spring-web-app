package org.vaadin.example.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;

@Route(value = "contact-form", layout = MainView.class)
@PageTitle("Contact form")
public class ContactForm extends FormLayout{
    private TextField fName = new TextField("First Name");
    private TextField lName = new TextField("Last Name");
    private EmailField email = new EmailField("Email");
    private ComboBox<Contact.Status> status = new ComboBox<>("Status");
    private ComboBox<Company> company = new ComboBox<>("Company");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button close = new Button("Cancel");

    public ContactForm() {
        addClassName("contact-form");
        add(fName, lName, email, status, company,
        //helper method for buttons
        createButtonsLayout());
    }
    

    public Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        //keyboard shortcuts for buttons
        save.addClickShortcut(Key.ENTER);
        return new HorizontalLayout(save, delete, close);
    }
     
}
