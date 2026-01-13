/*
 * Copyright (C) 2011 Jason von Nieda <jason@vonnieda.org>
 * 
 * This file is part of OpenPnP.
 * 
 * OpenPnP is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * OpenPnP is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with OpenPnP. If not, see
 * <http://www.gnu.org/licenses/>.
 * 
 * For more information about OpenPnP visit http://openpnp.org
 */

package org.openpnp.machine.reference.wizards;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.openpnp.Translations;
import org.openpnp.gui.components.ComponentDecorators;
import org.openpnp.gui.support.AbstractConfigurationWizard;
import org.openpnp.gui.support.IntegerConverter;
import org.openpnp.gui.support.LengthConverter;
import org.openpnp.machine.reference.ReferencePnpJobProcessor;
import org.openpnp.machine.reference.ReferencePnpJobProcessor.JobOrderHint;
import org.openpnp.machine.reference.ReferencePnpJobProcessor.ValidationMethod;
import org.openpnp.model.Configuration;
import org.openpnp.spi.Actuator;
import org.openpnp.spi.PnpJobPlanner.Strategy;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class ReferencePnpJobProcessorConfigurationWizard extends AbstractConfigurationWizard {
    private final ReferencePnpJobProcessor jobProcessor;
    private JComboBox<JobOrderHint> comboBoxJobOrder;
    private JComboBox<Strategy> comboBoxPlannerStrategy;
    private JTextField maxVisionRetriesTextField;
    private JTextField maxPlacementRetriesTextField;
    private JCheckBox steppingToNextMotion;
    private JCheckBox optimizeMultipleNozzles;
    private JCheckBox preRotateAllNozzles;
    private JTextField feederFaultLimitTextField;
    private JTextField feederFaultWindowSizeTextField;
    
    private JCheckBox validateZHeights;
    private JTextField zHeightTolerance;
    
    private JComboBox<ValidationMethod> comboBoxValidationMethod;
    private JComboBox<String> comboBoxZProbeActuator;
    private JLabel lblZProbeActuator;

    public ReferencePnpJobProcessorConfigurationWizard(ReferencePnpJobProcessor jobProcessor) {
        this.jobProcessor = jobProcessor;
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        JPanel panelGeneral = new JPanel();
        panelGeneral.setBorder(new TitledBorder(null, Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.GeneralPanel.Border.title"), TitledBorder.LEADING, //$NON-NLS-1$
                TitledBorder.TOP, null, null));
        contentPanel.add(panelGeneral);
        
        panelGeneral.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
                new RowSpec[] {
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,}));

        JLabel lblMaxPlacementRetries = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.MaxPlacementRetries")); //$NON-NLS-1$
        lblMaxPlacementRetries.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.MaxPlacementRetries.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblMaxPlacementRetries, "2, 2, right, default");

        maxPlacementRetriesTextField = new JTextField();
        panelGeneral.add(maxPlacementRetriesTextField, "4, 2");
        maxPlacementRetriesTextField.setColumns(10);

        // FIXME: this translation reference looks quite different to the one used below and shall be synchronized
        // !! if this translation reference is changed, change the one in ReferencePnPJobProcessor line 89 as well to keep both synchronized.
        JLabel lblJobOrder = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.JobOrder")); //$NON-NLS-1$
        lblJobOrder.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.JobOrder.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblJobOrder, "2, 4, right, default");

        comboBoxJobOrder = new JComboBox<JobOrderHint>(JobOrderHint.values());
        comboBoxJobOrder.setMaximumRowCount(10);
        panelGeneral.add(comboBoxJobOrder, "4, 4");

        JLabel lblPlannerStrategy = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.lblPlannerStrategy.text")); //$NON-NLS-1$
        lblPlannerStrategy.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.lblPlannerStrategy.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblPlannerStrategy, "2, 6, right, default");

        comboBoxPlannerStrategy = new JComboBox<Strategy>(Strategy.values());
        panelGeneral.add(comboBoxPlannerStrategy, "4, 6");

        JLabel lblMaxVisionRetries = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.MaxVisionRetries")); //$NON-NLS-1$
        lblMaxVisionRetries.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.MaxVisionRetries.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblMaxVisionRetries, "2, 8, right, default");

        maxVisionRetriesTextField = new JTextField();
        panelGeneral.add(maxVisionRetriesTextField, "4, 8");
        maxVisionRetriesTextField.setColumns(10);

        JLabel lblStepsMotion = new JLabel(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblStepsMotion.text")); //$NON-NLS-1$
        lblStepsMotion.setToolTipText(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblStepsMotion.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblStepsMotion, "2, 10, right, default");

        steppingToNextMotion = new JCheckBox(); 
        panelGeneral.add(steppingToNextMotion, "4, 10");

        JLabel lblOptimizeMultipleNozzles = new JLabel(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblOptimizeMultipleNozzles.text")); //$NON-NLS-1$
        lblOptimizeMultipleNozzles.setToolTipText(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblOptimizeMultipleNozzles.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblOptimizeMultipleNozzles, "2, 12, right, default");

        optimizeMultipleNozzles = new JCheckBox(); 
        panelGeneral.add(optimizeMultipleNozzles, "4, 12");

        JLabel lblPreRotateAllNozzles = new JLabel(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblPreRotateAllNozzles.text")); //$NON-NLS-1$
        lblPreRotateAllNozzles.setToolTipText(Translations.getString("ReferencePnpJobProcessorConfigurationWizard.lblPreRotateAllNozzles.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblPreRotateAllNozzles, "2, 14, right, default");

        preRotateAllNozzles = new JCheckBox(); 
        panelGeneral.add(preRotateAllNozzles, "4, 14");

        JLabel lblFeederFaultLimit = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.FeederFaultLimit")); //$NON-NLS-1$
        lblFeederFaultLimit.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.FeederFaultLimit.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblFeederFaultLimit, "2, 16, right, default");

        feederFaultLimitTextField = new JTextField();
        panelGeneral.add(feederFaultLimitTextField, "4, 16");
        feederFaultLimitTextField.setColumns(10);

        JLabel lblFeederFaultWindowSize = new JLabel(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.FeederFaultWindowSize")); //$NON-NLS-1$
        lblFeederFaultWindowSize.setToolTipText(Translations.getString("MachineSetup.JobProcessors.ReferencePnpJobProcessor.Label.FeederFaultWindowSize.toolTipText")); //$NON-NLS-1$
        panelGeneral.add(lblFeederFaultWindowSize, "2, 18, right, default");

        feederFaultWindowSizeTextField = new JTextField();
        panelGeneral.add(feederFaultWindowSizeTextField, "4, 18");
        feederFaultWindowSizeTextField.setColumns(10);
        

        // Safety Panel
        JPanel panelSafety = new JPanel();
        panelSafety.setBorder(new TitledBorder(null, "Safety Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPanel.add(panelSafety);
        
        panelSafety.setLayout(new FormLayout(new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,
                FormSpecs.RELATED_GAP_COLSPEC,
                FormSpecs.DEFAULT_COLSPEC,},
                new RowSpec[] {
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,
                        FormSpecs.RELATED_GAP_ROWSPEC,
                        FormSpecs.DEFAULT_ROWSPEC,}));
        
        JLabel lblValidateZ = new JLabel("Validate sane Z-heights");
        lblValidateZ.setToolTipText("If enabled, the Z-height of boards and feeders will be validated against the detected height before starting a job.");
        panelSafety.add(lblValidateZ, "2, 2, right, default");
        
        validateZHeights = new JCheckBox();
        panelSafety.add(validateZHeights, "4, 2");
        
        JLabel lblValidationMethod = new JLabel("Method");
        panelSafety.add(lblValidationMethod, "2, 4, right, default");
        
        comboBoxValidationMethod = new JComboBox<ValidationMethod>(ValidationMethod.values());
        panelSafety.add(comboBoxValidationMethod, "4, 4");
        
        lblZProbeActuator = new JLabel("Z Probe");
        lblZProbeActuator.setToolTipText("The actuator to use for Z probing.");
        panelSafety.add(lblZProbeActuator, "2, 6, right, default");
        
        comboBoxZProbeActuator = new JComboBox<>();
        panelSafety.add(comboBoxZProbeActuator, "4, 6");
        
        // Populate Actuators
        comboBoxZProbeActuator.addItem(""); // Allow empty
        for (Actuator actuator : Configuration.get().getMachine().getActuators()) {
            comboBoxZProbeActuator.addItem(actuator.getName());
        }

        JLabel lblZTolerance = new JLabel("Tolerance");
        lblZTolerance.setToolTipText("The maximum allowed difference between configured and detected Z-height.");
        panelSafety.add(lblZTolerance, "2, 8, right, default");
        
        zHeightTolerance = new JTextField();
        panelSafety.add(zHeightTolerance, "4, 8");
        zHeightTolerance.setColumns(10);
        
        // Update visibility
        comboBoxValidationMethod.addActionListener(e -> updateVisibility());
        updateVisibility();
    }
    
    private void updateVisibility() {
        boolean isZProbe = (comboBoxValidationMethod.getSelectedItem() == ValidationMethod.ZProbeActuator);
        lblZProbeActuator.setVisible(isZProbe);
        comboBoxZProbeActuator.setVisible(isZProbe);
    }

    @Override
    public void createBindings() {
        IntegerConverter intConverter = new IntegerConverter();
        LengthConverter lengthConverter = new LengthConverter();

        addWrappedBinding(jobProcessor, "jobOrder", comboBoxJobOrder, "selectedItem");
        addWrappedBinding(jobProcessor.planner, "strategy", comboBoxPlannerStrategy, "selectedItem");
        addWrappedBinding(jobProcessor, "maxVisionRetries", maxVisionRetriesTextField, "text", intConverter);
        addWrappedBinding(jobProcessor, "maxPlacementRetries", maxPlacementRetriesTextField, "text", intConverter);
        addWrappedBinding(jobProcessor, "steppingToNextMotion", steppingToNextMotion, "selected");
        addWrappedBinding(jobProcessor, "optimizeMultipleNozzles", optimizeMultipleNozzles, "selected");
        addWrappedBinding(jobProcessor, "preRotateAllNozzles", preRotateAllNozzles, "selected");
        addWrappedBinding(jobProcessor, "feederFaultLimit", feederFaultLimitTextField, "text", intConverter);
        addWrappedBinding(jobProcessor, "feederFaultWindowSize", feederFaultWindowSizeTextField, "text", intConverter);
        
        addWrappedBinding(jobProcessor, "validateZHeights", validateZHeights, "selected");
        addWrappedBinding(jobProcessor, "validationMethod", comboBoxValidationMethod, "selectedItem");
        addWrappedBinding(jobProcessor, "ZProbeActuatorName", comboBoxZProbeActuator, "selectedItem");
        addWrappedBinding(jobProcessor, "ZHeightTolerance", zHeightTolerance, "text", lengthConverter);

        ComponentDecorators.decorateWithAutoSelect(maxVisionRetriesTextField);
        ComponentDecorators.decorateWithAutoSelectAndLengthConversion(zHeightTolerance);
    }
}
