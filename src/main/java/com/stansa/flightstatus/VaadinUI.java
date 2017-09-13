package com.stansa.flightstatus;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.vaadin.shared.ui.grid.HeightMode;
import java.util.ArrayList;

@SpringUI
public class VaadinUI extends UI {

	private final flightstatusRepository repo;

	private final redyeEditor editor;

	final Grid<flightstatus> grid;

	final TextField filter;



	@Autowired
	public VaadinUI(flightstatusRepository repo, redyeEditor editor) {
		this.repo = repo;
		this.editor = editor;
		this.grid = new Grid<>(flightstatus.class);
		this.filter = new TextField();

	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

		//grid.setHeight(300, Unit.PIXELS);
		grid.setHeightMode( HeightMode.ROW );
		grid.setHeightByRows(10);
		grid.setWidth("1000px");

		grid.setColumns("id", "classes", "probabilities", "exception", "eventProcessedUtcTime");
		grid.getColumn("id").setCaption("Batch Id");
		grid.getColumn("classes").setCaption("Redye?");
		grid.getColumn("probabilities").setCaption("Probability of Success");
		grid.getColumn("exception").setCaption("Exception");
		grid.getColumn("eventProcessedUtcTime").setCaption("Timestamp (UTC time)");

		grid.sort("eventProcessedUtcTime", SortDirection.DESCENDING);


		filter.setPlaceholder("Filter by Batch Id");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.BLUR);
		filter.addValueChangeListener(e -> listBatches(e.getValue()));

		// Connect selected Batch to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editBatch(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		//addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listBatches(filter.getValue());
		});

		// Initialize listing
		if (filter.getValue() == null) {
			listBatches(null);
		} else {
			listBatches(filter.getValue());
		}
	}

	// tag::listCustomers[]
	void listBatches(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			try {
				grid.setItems(repo.findOne(filterText));
			} catch (Exception e) {
				grid.setItems(new ArrayList<flightstatus>(1));
			}
		}
	}
	// end::listBatches[]

}
