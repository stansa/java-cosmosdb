package com.stansa.flightstatus;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import com.microsoft.azure.documentdb.DocumentClient;

/**
 * A simple example to introduce building forms. As your real application is probably much
 * more complicated than this example, you could re-use this form in multiple places. This
 * example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Viritin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class redyeEditor extends VerticalLayout {

	@Autowired
	private final flightstatusRepository repository;

	// The Azure Cosmos DB Client
	private static DocumentClient documentClient = DocumentClientFactory
			.getDocumentClient();

	/**
	 * The currently edited customer
	 */
	private flightstatus flightstatusBatch;



	List<String> declined = new ArrayList<>();



	ComboBox<String> exception =
			new ComboBox<>("Select an Exception");



	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	//Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel);

	Binder<flightstatus> binder = new Binder<>(flightstatus.class);

	@Autowired
	public redyeEditor(flightstatusRepository repository) {
		this.repository = repository;

		declined.add("Option 1");
		declined.add("Option 2");
		declined.add("Option 3");
		declined.add("Option 4");
		declined.add("Option 5");

		exception.setItems(declined);

		addComponents(exception, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> updateException(flightstatusBatch));
		//delete.addClickListener(e -> repository.delete(flightstatus));
		cancel.addClickListener(e -> editBatch(flightstatusBatch));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void updateException(flightstatus batch) {

		RedyeDao rdao = new RedyeDao();
		rdao.update(batch.getId(),batch.getForecastedarrtime());

	}

	public final void editBatch(flightstatus c) {
		if (c == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = c.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			flightstatusBatch = repository.findOne(c.getId());
		}
		else {
			flightstatusBatch = c;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(flightstatusBatch);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();

	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		//delete.addClickListener(e -> h.onChange());
	}

}
