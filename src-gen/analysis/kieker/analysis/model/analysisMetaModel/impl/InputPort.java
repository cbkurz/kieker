/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package kieker.analysis.model.analysisMetaModel.impl;

import java.util.Collection;

import kieker.analysis.model.analysisMetaModel.IAnalysisMetaModelPackage;
import kieker.analysis.model.analysisMetaModel.IConnector;
import kieker.analysis.model.analysisMetaModel.IInputPort;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Port</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link kieker.analysis.model.analysisMetaModel.impl.InputPort#getInConnector <em>In Connector</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InputPort extends Port implements IInputPort {
	/**
	 * The cached value of the '{@link #getInConnector() <em>In Connector</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInConnector()
	 * @generated
	 * @ordered
	 */
	protected EList<IConnector> inConnector;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InputPort() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IAnalysisMetaModelPackage.Literals.INPUT_PORT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IConnector> getInConnector() {
		if (inConnector == null) {
			inConnector = new EObjectWithInverseResolvingEList<IConnector>(IConnector.class, this, IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR, IAnalysisMetaModelPackage.CONNECTOR__DST_INPUT_PORT);
		}
		return inConnector;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInConnector()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				return ((InternalEList<?>)getInConnector()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				return getInConnector();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				getInConnector().clear();
				getInConnector().addAll((Collection<? extends IConnector>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				getInConnector().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IAnalysisMetaModelPackage.INPUT_PORT__IN_CONNECTOR:
				return inConnector != null && !inConnector.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //InputPort
