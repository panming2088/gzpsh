/* Copyright 2015 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.augurit.agmobile.gzps.addcomponent;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.augurit.agmobile.gzps.common.constant.LayerUrlConstant;
import com.augurit.agmobile.gzps.R;
import com.augurit.agmobile.gzps.addcomponent.model.ComponentMap;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.Layer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureEditResult;
import com.esri.core.map.FeatureSet;
import com.esri.core.map.FeatureTemplate;
import com.esri.core.map.FeatureType;
import com.esri.core.map.Graphic;
import com.esri.core.renderer.Renderer;
import com.esri.core.symbol.FillSymbol;
import com.esri.core.symbol.LineSymbol;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.Symbol;
import com.esri.core.symbol.SymbolHelper;
import com.esri.core.tasks.SpatialRelationship;
import com.esri.core.tasks.ags.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this sample is to demonstrate how to create features (point, polyline, polygon) with the ArcGIS for
 * Android API. The sample supports template based editing for the three types of feature layers (point, line and
 * polygon).
 * <p>
 * Tap the '+' icon in the action bar to start adding a feature. A list of available templates is displayed
 * showing the templates' symbol to allow you to quickly select the required feature to add to the map.
 * <p>
 * When adding a point feature, tap the map to position the feature. Tapping the map again moves the point to
 * the new position.
 * <p>
 * When adding polygon or polyline features:
 * <ul>
 * <li>add a new vertex by simply tapping on a new location on the map;
 * <li>move an existing vertex by tapping it and then tapping its new location on the map;
 * <li>delete an existing vertex by tapping it and then tapping the trash can icon on the action bar.
 * </ul>
 * Additional points are drawn at the midpoint of each line. A midpoint can be moved by tapping the midpoint and then
 * tapping its new location on the map.
 * <p>
 * In addition to the trash can, the action bar presents the following icons when editing a feature:
 * <ul>
 * <li>floppy disk icon to Save the feature by uploading it to the server;
 * <li>'X' icon to Discard the feature;
 * <li>undo icon to Undo the last action performed (i.e. the last addition, move or deletion of a point).
 * </ul>
 * Whenever a feature is being added, a long-press on the map displays a magnifier that allows a location to be selected
 * more accurately.
 */
@Deprecated
public class AddComponentFragment extends Fragment {

    protected static final String TAG = "EditGraphicElements";

    private static final String TAG_DIALOG_FRAGMENTS = "dialog";

    private static final String KEY_MAP_STATE = "com.esri.MapState";

    private static int ATTRIBUTE_EDITOR_DIALOG_ID = 1;

    private enum EditMode {
        NONE, POINT, POLYLINE, POLYGON, SAVING
    }




    Menu mOptionsMenu;

    MapView mMapView;

//    ArcGISFeatureLayer fl3;

    String mMapState;

    List<ComponentMap> componentMapList = new ArrayList<>();

    DialogFragment mDialogFragment;

    GraphicsLayer mGraphicsLayerEditing;

    ArrayList<Point> mPoints = new ArrayList<Point>();

    ArrayList<Point> mMidPoints = new ArrayList<Point>();

    boolean mMidPointSelected = false;

    boolean mVertexSelected = false;

    int mInsertingIndex;

    EditMode mEditMode;

    boolean mClosingTheApp = false;

    ArrayList<EditingStates> mEditingStates = new ArrayList<EditingStates>();

    ArrayList<FeatureTypeData> mFeatureTypeList;

    ArrayList<FeatureTemplate> mTemplateList;

    ArrayList<ArcGISFeatureLayer> mFeatureLayerList;

    FeatureTemplate mTemplate;

    ArcGISFeatureLayer mTemplateLayer;

    private Point pointClicked;
    // android components
    private LayoutInflater inflator;
    private AttributeListAdapter listAdapter;
    private ListView listView;
    private View listLayout;
    private boolean selectFeatureSuccess = false;
    private ArcGISFeatureLayer selectedFeatureLayer = null;
    String curObjectName = "";

    private boolean inited = false;

    SimpleMarkerSymbol mRedMarkerSymbol = new SimpleMarkerSymbol(Color.RED, 20, SimpleMarkerSymbol.STYLE.CIRCLE);

    SimpleMarkerSymbol mBlackMarkerSymbol = new SimpleMarkerSymbol(Color.BLACK, 20, SimpleMarkerSymbol.STYLE.CIRCLE);

    SimpleMarkerSymbol mGreenMarkerSymbol = new SimpleMarkerSymbol(Color.GREEN, 15, SimpleMarkerSymbol.STYLE.CIRCLE);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return View.inflate(getActivity(), R.layout.fragment_addcomponent, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEditMode = EditMode.NONE;

        if (savedInstanceState == null) {
            mMapState = null;
        } else {
            mMapState = savedInstanceState.getString(KEY_MAP_STATE);

            // If activity is destroyed and recreated, we discard any edit that was in progress.
            // Because of that, we also dismiss any dialog that may be in progress, because it would be related to an edit.
            Fragment dialogFrag = getFragmentManager().findFragmentByTag(TAG_DIALOG_FRAGMENTS);
            if (dialogFrag != null) {
                ((DialogFragment) dialogFrag).dismiss();
            }
        }

//        curObjectName = getIntent().getStringExtra("objectName");


        // Create status listener for feature layers
        OnStatusChangedListener statusChangedListener = new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onStatusChanged(Object source, STATUS status) {
                if (status == STATUS.INITIALIZED) {

                }
                /*if (status == STATUS.INITIALIZED) {
                    listTemplates();
                    if (ListUtil.isEmpty(mFeatureLayerList)) {
                        return;
                    }
                    if (!StringUtil.isEmpty(curObjectName)) {
                        for (ComponentMap objectMap : componentMapList) {
                            if (objectMap.getName().equals(curObjectName)) {
                                int position = -1;
                                for (int i = 0; i < mFeatureLayerList.size(); i++) {
                                    if (mFeatureLayerList.get(i).getUrl().equals(objectMap.getUrl())) {
                                        position = i;
                                        break;
                                    }
                                }
                                if (position != -1) {
                                    selectedFeatureLayer = mFeatureLayerList.get(position);
                                    initAddEnv(position);
                                }
                            }
                        }
                    }
                }*/
            }
        };

        SimpleMarkerSymbol sms = new SimpleMarkerSymbol(Color.RED, 8, SimpleMarkerSymbol.STYLE.CIRCLE);
        SimpleLineSymbol sls = new SimpleLineSymbol(Color.RED, 3);
        // Show feature selected with outline symbol
        SimpleFillSymbol sfs = new SimpleFillSymbol(Color.argb(200, 255, 0, 0));
        sfs.setOutline(new SimpleLineSymbol(Color.argb(100, 0, 0, 255), 5));




        // Find MapView and add feature layers
        mMapView = (MapView) view.findViewById(R.id.map);
        ArcGISTiledMapServiceLayer basemap = new ArcGISTiledMapServiceLayer(LayerUrlConstant.mapServerUrl);
        mMapView.addLayer(basemap);

        // Set listeners on MapView
        mMapView.setOnStatusChangedListener(new OnStatusChangedListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onStatusChanged(final Object source, final STATUS status) {
                if (STATUS.INITIALIZED == status) {
                    if (source instanceof MapView) {
                        mGraphicsLayerEditing = new GraphicsLayer();
                        mMapView.addLayer(mGraphicsLayerEditing);
                        mMapView.centerAt(new Point(12615839.717853284, 2646572.808021581), true);
                        mMapView.setResolution(1.910925817121921);
                    }
                }
            }
        });
        mMapView.setOnTouchListener(new MyTouchListener(getActivity(), mMapView));

        // If map state (center and resolution) has been stored, update the MapView with this state
        if (!TextUtils.isEmpty(mMapState)) {
            mMapView.restoreState(mMapState);
        }

        inflator = LayoutInflater.from(getActivity());


        // Create a new AttributeListAdapter when the feature layer is initialized
        /*if (fl3.isInitialized()) {
            listAdapter = new AttributeListAdapter(this, fl3.getFields(), fl3.getTypes(),
                    fl3.getTypeIdField());
        } else {
            fl3.setOnStatusChangedListener(new OnStatusChangedListener() {
                private static final long serialVersionUID = 1L;

                public void onStatusChanged(Object source, STATUS status) {
                    if (status == STATUS.INITIALIZED) {
                        listAdapter = new AttributeListAdapter(AddComponentActivity.this, fl3.getFields(), fl3
                                .getTypes(), fl3.getTypeIdField());
                    }
                }
            });
        }*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.actions, menu);
        mOptionsMenu = menu;
        updateActionBar();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                actionAdd();
                return true;
            case R.id.action_save:
//                actionSave();
                showAddDialog();
                return true;
            case R.id.action_discard:
                actionDiscard();
                return true;
            case R.id.action_delete:
                actionDelete();
                return true;
            case R.id.action_undo:
                actionUndo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        if (mEditMode != EditMode.NONE && mEditMode != EditMode.SAVING && mEditingStates.size() > 0) {
            // There's an edit in progress, so ask for confirmation
            mClosingTheApp = true;
            showConfirmDiscardDialogFragment();
        } else {
            // No edit in progress, so allow the app to be closed
//            super.onBackPressed();
            getActivity().finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.unpause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MAP_STATE, mMapView.retainState());
    }

    /**
     * Handles the 'Add' action.
     */
    private void actionAdd() {
        listTemplates();
        showFeatureTypeDialogFragment();
    }

    /**
     * Handles the 'Discard' action.
     */
    private void actionDiscard() {
        if (mEditingStates.size() > 0) {
            // There's an edit in progress, so ask for confirmation
            mClosingTheApp = false;
            showConfirmDiscardDialogFragment();
        } else {
            // No edit in progress, so just exit edit mode
            exitEditMode();
        }
    }

    /**
     * Handles the 'Delete' action.
     */
    private void actionDelete() {
        if (!mVertexSelected) {
            mPoints.remove(mPoints.size() - 1); // remove last vertex
        } else {
            mPoints.remove(mInsertingIndex); // remove currently selected vertex
        }
        mMidPointSelected = false;
        mVertexSelected = false;
        mEditingStates.add(new EditingStates(mPoints, mMidPointSelected, mVertexSelected, mInsertingIndex));
        refresh();
    }

    /**
     * Handles the 'Undo' action.
     */
    private void actionUndo() {
        mEditingStates.remove(mEditingStates.size() - 1);
        mPoints.clear();
        if (mEditingStates.size() == 0) {
            mMidPointSelected = false;
            mVertexSelected = false;
            mInsertingIndex = 0;
        } else {
            EditingStates state = mEditingStates.get(mEditingStates.size() - 1);
            mPoints.addAll(state.points);
            Log.d(TAG, "# of points = " + mPoints.size());
            mMidPointSelected = state.midPointSelected;
            mVertexSelected = state.vertexSelected;
            mInsertingIndex = state.insertingIndex;
        }
        refresh();
    }

    /**
     * Handles the 'Save' action. The edits made are applied and hence saved on the server.
     */
    private void applySave() {
        Map<String, Object> attrs = new HashMap<>();

        // loop through each attribute and set the new values if they have
        // changed
        for (int i = 0; i < listAdapter.getCount(); i++) {

            AttributeItem item = (AttributeItem) listAdapter.getItem(i);
            String value;

            // check to see if the View has been set
            if (item.getView() != null) {

                // determine field type and therefore View type
                if (item.getField().getName().equals(mTemplateLayer.getTypeIdField())) {
                    // drop down spinner

                    Spinner spinner = (Spinner) item.getView();
                    // get value for the type
                    String typeName = spinner.getSelectedItem().toString();
                    value = FeatureLayerUtils.returnTypeIdFromTypeName(mTemplateLayer.getTypes(), typeName);


                } else if (FeatureLayerUtils.FieldType.determineFieldType(item.getField()) == FeatureLayerUtils.FieldType.DATE) {
                    // date

                    Button dateButton = (Button) item.getView();
                    value = dateButton.getText().toString();

                } else {
                    // edit text

                    EditText editText = (EditText) item.getView();
                    value = editText.getText().toString();

                }
                boolean hasChanged = FeatureLayerUtils.setAttribute(attrs, new Graphic(null, null),
                        item.getField(), value, listAdapter.formatter);
            }
        }


        Graphic g;

        if (mEditMode == EditMode.POINT) {
            // For a point, just create a Graphic from the point
            g = new Graphic(mPoints.get(0), null, attrs);
        } else {
            // For polylines and polygons, create a MultiPath from the points...
            MultiPath multipath;
            if (mEditMode == EditMode.POLYLINE) {
                multipath = new Polyline();
            } else if (mEditMode == EditMode.POLYGON) {
                multipath = new Polygon();
            } else {
                return;
            }
            multipath.startPath(mPoints.get(0));
            for (int i = 1; i < mPoints.size(); i++) {
                multipath.lineTo(mPoints.get(i));
            }

            // ...then simplify the geometry and create a Graphic from it
            Geometry geom = GeometryEngine.simplify(multipath, mMapView.getSpatialReference());
            g = new Graphic(geom, null, attrs);
        }

        mEditMode = EditMode.SAVING;
        updateActionBar();
        // Now add the Graphic to the layer
        mTemplateLayer.applyEdits(new Graphic[]{g}, null, null, new CallbackListener<FeatureEditResult[][]>() {

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
                completeSaveAction(null);
                selectFeatureSuccess = false;
                // close the dialog
                getActivity().dismissDialog(ATTRIBUTE_EDITOR_DIALOG_ID);
            }

            @Override
            public void onCallback(FeatureEditResult[][] results) {
                completeSaveAction(results);
                selectFeatureSuccess = false;
                // close the dialog
                getActivity().dismissDialog(ATTRIBUTE_EDITOR_DIALOG_ID);
            }

        });

    }

    private void showAddDialog() {
        listAdapter = new AttributeListAdapter(getActivity(), mTemplateLayer.getFields(), mTemplateLayer.getTypes(),
                mTemplateLayer.getTypeIdField());
        // set new data and notify adapter that data has changed
        listAdapter.setFeatureSet(null);
        listAdapter.notifyDataSetChanged();

        // This callback is not run in the main UI thread. All GUI
        // related events must run in the UI thread,
        // therefore use the Activity.runOnUiThread() method. See
        // http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)
        // for more information.
        getActivity().runOnUiThread(new Runnable() {

            public void run() {

                // show the editor dialog.
                java.util.Random r = new java.util.Random();
                ATTRIBUTE_EDITOR_DIALOG_ID = r.nextInt();
                getActivity().showDialog(ATTRIBUTE_EDITOR_DIALOG_ID);

            }
        });
    }

    /**
     * Reports result of 'Save' action to user and exit edit mode.
     *
     * @param results Results of applyEdits operation, or null if it failed.
     */
    void completeSaveAction(final FeatureEditResult[][] results) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (results != null) {
                    if (results[0][0].isSuccess()) {
                        String msg = getActivity().getString(R.string.saved);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    } else {
                        EditFailedDialogFragment frag = new EditFailedDialogFragment();
                        mDialogFragment = frag;
                        frag.setMessage(results[0][0].getError().getDescription());
                        frag.show(getFragmentManager(), TAG_DIALOG_FRAGMENTS);
                    }
                }
                exitEditMode();
            }
        });
    }

    /**
     * Shows dialog asking user to select the type of feature to add.
     */
    private void showFeatureTypeDialogFragment() {
        FeatureTypeDialogFragment frag = new FeatureTypeDialogFragment();
        mDialogFragment = frag;
        frag.setListListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initAddEnv(position);
            }

        });
        frag.setListAdapter(new FeatureTypeListAdapter(getActivity(), mFeatureTypeList));
        frag.show(getFragmentManager(), TAG_DIALOG_FRAGMENTS);
    }

    /**
     * Shows dialog asking user to confirm discarding the feature being added.
     */
    private void showConfirmDiscardDialogFragment() {
        ConfirmDiscardDialogFragment frag = new ConfirmDiscardDialogFragment();
        mDialogFragment = frag;
        frag.setYesListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDialogFragment.dismiss();
                if (mClosingTheApp) {
                    getActivity().finish();
                } else {
                    exitEditMode();
                }
            }

        });
        frag.show(getFragmentManager(), TAG_DIALOG_FRAGMENTS);
    }

    private void initAddEnv(int position) {
        mTemplate = mTemplateList.get(position);
        mTemplateLayer = mFeatureLayerList.get(position);

        FeatureTypeData featureType = mFeatureTypeList.get(position);
        Symbol symbol = featureType.getSymbol();
        if (symbol instanceof MarkerSymbol) {
            mEditMode = EditMode.POINT;
        } else if (symbol instanceof LineSymbol) {
            mEditMode = EditMode.POLYLINE;
        } else if (symbol instanceof FillSymbol) {
            mEditMode = EditMode.POLYGON;
        }
        clear();
        mDialogFragment.dismiss();

        // Set up use of magnifier on a long press on the map
        mMapView.setShowMagnifierOnLongPress(true);
    }

    /**
     * Exits the edit mode state.
     */
    void exitEditMode() {
        mEditMode = EditMode.NONE;
        clear();
        mMapView.setShowMagnifierOnLongPress(false);
    }

    /**
     * Using this method all the feature templates in the layer are listed. From the MapView we get all the layers in an
     * array. Check which of them are instances of ArcGISFeatureLayer. From the feature layer we get all the templates and
     * populate the list. Since we go through all the layers we obtain feature templates for all layers.
     */
    private void listTemplates() {
        mFeatureTypeList = new ArrayList<FeatureTypeData>();
        mTemplateList = new ArrayList<FeatureTemplate>();
        mFeatureLayerList = new ArrayList<ArcGISFeatureLayer>();

        // Loop on all the layers in the MapView
        Layer[] layers = mMapView.getLayers();
        for (Layer l : layers) {

            // Check if this is an ArcGISFeatureLayer
            if (l instanceof ArcGISFeatureLayer) {
                Log.d(TAG, l.getUrl());
                ArcGISFeatureLayer featureLayer = (ArcGISFeatureLayer) l;

                // Loop on all feature types in the layer
                FeatureType[] types = featureLayer.getTypes();
                if (types == null
                        || types.length == 0) {
                    // If no templates provided by feature types, get templates from the layer itself
//                    if (mFeatureTypeList.size() == 0) {
                    addTemplates(featureLayer, featureLayer.getTemplates());
//                    }
                } else {
                    for (FeatureType featureType : types) {
                        // Save data for each template for this feature type
                        addTemplates(featureLayer, featureType.getTemplates());
                    }
                }

            }
        }
    }

    /**
     * Saves data for a set of feature templates.
     *
     * @param featureLayer Feature layer that the templates belong to.
     * @param templates    Array of templates to save.
     */
    private void addTemplates(ArcGISFeatureLayer featureLayer, FeatureTemplate[] templates) {
        String objectName = "";
        for (ComponentMap componentMap : componentMapList) {
            if (featureLayer.getUrl().equals(componentMap.getUrl())) {
                objectName = componentMap.getName();
                break;
            }
        }
        for (FeatureTemplate featureTemplate : templates) {
            String name = featureTemplate.getName();
            Graphic g = featureLayer.createFeatureWithTemplate(featureTemplate, null);
            Renderer renderer = featureLayer.getRenderer();
            Symbol symbol = renderer.getSymbol(g);

            final int WIDTH_IN_DP_UNITS = 30;
            final float scale = getResources().getDisplayMetrics().density;
            final int widthInPixels = (int) (WIDTH_IN_DP_UNITS * scale + 0.5f);
            Bitmap bitmap = SymbolHelper.getLegendImage(symbol, widthInPixels, widthInPixels);

            mFeatureTypeList.add(new FeatureTypeData(bitmap, name, symbol));
            mTemplateList.add(featureTemplate);
            mFeatureLayerList.add(featureLayer);
        }
    }

    /**
     * Redraws everything on the mGraphicsLayerEditing layer following an edit and updates the items shown on the action
     * bar.
     */
    void refresh() {
        if (mGraphicsLayerEditing != null) {
            mGraphicsLayerEditing.removeAll();
        }
        drawPolylineOrPolygon();
        drawMidPoints();
        drawVertices();

        updateActionBar();
    }

    /**
     * Updates action bar to show actions appropriate for current state of the app.
     */
    private void updateActionBar() {
        if (mEditMode == EditMode.NONE || mEditMode == EditMode.SAVING) {
            // We are not editing
            if (mEditMode == EditMode.NONE) {
                showAction(R.id.action_add, true);
            } else {
                showAction(R.id.action_add, false);
            }
            showAction(R.id.action_discard, false);
            showAction(R.id.action_save, false);
            showAction(R.id.action_delete, false);
            showAction(R.id.action_undo, false);
        } else {
            // We are editing
            showAction(R.id.action_add, false);
            showAction(R.id.action_discard, true);
            if (isSaveValid()) {
                showAction(R.id.action_save, true);
            } else {
                showAction(R.id.action_save, false);
            }
            if (mEditMode != EditMode.POINT && mPoints.size() > 0 && !mMidPointSelected) {
                showAction(R.id.action_delete, true);
            } else {
                showAction(R.id.action_delete, false);
            }
            if (mEditingStates.size() > 0) {
                showAction(R.id.action_undo, true);
            } else {
                showAction(R.id.action_undo, false);
            }
        }
    }

    /**
     * Shows or hides an action bar item.
     *
     * @param resId Resource ID of the item.
     * @param show  true to show the item, false to hide it.
     */
    private void showAction(int resId, boolean show) {
        MenuItem item = mOptionsMenu.findItem(resId);
        item.setEnabled(show);
        item.setVisible(show);
    }

    /**
     * Checks if it's valid to save the feature currently being created.
     *
     * @return true if valid.
     */
    private boolean isSaveValid() {
        int minPoints;
        switch (mEditMode) {
            case POINT:
                minPoints = 1;
                break;
            case POLYGON:
                minPoints = 3;
                break;
            case POLYLINE:
                minPoints = 2;
                break;
            default:
                return false;
        }
        return mPoints.size() >= minPoints;
    }

    /**
     * Draws polyline or polygon (dependent on current mEditMode) between the vertices in mPoints.
     */
    private void drawPolylineOrPolygon() {
        Graphic graphic;
        MultiPath multipath;

        // Create and add graphics layer if it doesn't already exist
        if (mGraphicsLayerEditing == null) {
            mGraphicsLayerEditing = new GraphicsLayer();
            mMapView.addLayer(mGraphicsLayerEditing);
        }

        if (mPoints.size() > 1) {

            // Build a MultiPath containing the vertices
            if (mEditMode == EditMode.POLYLINE) {
                multipath = new Polyline();
            } else {
                multipath = new Polygon();
            }
            multipath.startPath(mPoints.get(0));
            for (int i = 1; i < mPoints.size(); i++) {
                multipath.lineTo(mPoints.get(i));
            }

            // Draw it using a line or fill symbol
            if (mEditMode == EditMode.POLYLINE) {
                graphic = new Graphic(multipath, new SimpleLineSymbol(Color.BLACK, 4));
            } else {
                SimpleFillSymbol simpleFillSymbol = new SimpleFillSymbol(Color.YELLOW);
                simpleFillSymbol.setAlpha(100);
                simpleFillSymbol.setOutline(new SimpleLineSymbol(Color.BLACK, 4));
                graphic = new Graphic(multipath, (simpleFillSymbol));
            }
            mGraphicsLayerEditing.addGraphic(graphic);
        }
    }

    /**
     * Draws mid-point half way between each pair of vertices in mPoints.
     */
    private void drawMidPoints() {
        int index;
        Graphic graphic;

        mMidPoints.clear();
        if (mPoints.size() > 1) {

            // Build new list of mid-points
            for (int i = 1; i < mPoints.size(); i++) {
                Point p1 = mPoints.get(i - 1);
                Point p2 = mPoints.get(i);
                mMidPoints.add(new Point((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2));
            }
            if (mEditMode == EditMode.POLYGON && mPoints.size() > 2) {
                // Complete the circle
                Point p1 = mPoints.get(0);
                Point p2 = mPoints.get(mPoints.size() - 1);
                mMidPoints.add(new Point((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2));
            }

            // Draw the mid-points
            index = 0;
            for (Point pt : mMidPoints) {
                if (mMidPointSelected && mInsertingIndex == index) {
                    graphic = new Graphic(pt, mRedMarkerSymbol);
                } else {
                    graphic = new Graphic(pt, mGreenMarkerSymbol);
                }
                mGraphicsLayerEditing.addGraphic(graphic);
                index++;
            }
        }
    }

    /**
     * Draws point for each vertex in mPoints.
     */
    private void drawVertices() {
        int index = 0;
        SimpleMarkerSymbol symbol;

        for (Point pt : mPoints) {
            if (mVertexSelected && index == mInsertingIndex) {
                // This vertex is currently selected so make it red
                symbol = mRedMarkerSymbol;
            } else if (index == mPoints.size() - 1 && !mMidPointSelected && !mVertexSelected) {
                // Last vertex and none currently selected so make it red
                symbol = mRedMarkerSymbol;
            } else {
                // Otherwise make it black
                symbol = mBlackMarkerSymbol;
            }
            Graphic graphic = new Graphic(pt, symbol);
            mGraphicsLayerEditing.addGraphic(graphic);
            index++;
        }
    }

    /**
     * Clears feature editing data and updates action bar.
     */
    void clear() {
        // Clear feature editing data
        mPoints.clear();
        mMidPoints.clear();
        mEditingStates.clear();

        mMidPointSelected = false;
        mVertexSelected = false;
        mInsertingIndex = 0;

        if (mGraphicsLayerEditing != null) {
            mGraphicsLayerEditing.removeAll();
        }

        // Update action bar to reflect the new state
        updateActionBar();
        int resId;
        switch (mEditMode) {
            case POINT:
                resId = R.string.title_add_point;
                break;
            case POLYGON:
                resId = R.string.title_add_polygon;
                break;
            case POLYLINE:
                resId = R.string.title_add_polyline;
                break;
            case NONE:
            default:
                resId = R.string.app_name;
                break;
        }
//        getActionBar().setTitle(resId);
    }

    /**
     * An instance of this class is created when a new point is added/moved/deleted. It records the state of editing at
     * that time and allows edit operations to be undone.
     */
    private class EditingStates {
        ArrayList<Point> points = new ArrayList<Point>();

        boolean midPointSelected = false;

        boolean vertexSelected = false;

        int insertingIndex;

        public EditingStates(ArrayList<Point> points, boolean midpointselected, boolean vertexselected, int insertingindex) {
            this.points.addAll(points);
            this.midPointSelected = midpointselected;
            this.vertexSelected = vertexselected;
            this.insertingIndex = insertingindex;
        }
    }

    /**
     * The MapView's touch listener.
     */
    private class MyTouchListener extends MapOnTouchListener {
        MapView mapView;

        public MyTouchListener(Context context, MapView view) {
            super(context, view);
            mapView = view;
        }

        @Override
        public boolean onLongPressUp(MotionEvent point) {
            handleTap(point);
            super.onLongPressUp(point);
            return true;
        }

        @Override
        public boolean onSingleTap(final MotionEvent e) {
//            Point point = mMapView.getCenter();
//            double resolution = mMapView.getResolution();
            handleTap(e);
            return true;
        }

        /***
         * Handle a tap on the map (or the end of a magnifier long-press event).
         *
         * @param e The point that was tapped.
         */
        private void handleTap(final MotionEvent e) {

            // Ignore the tap if we're not creating a feature just now
            if (mEditMode == EditMode.NONE || mEditMode == EditMode.SAVING) {
                selectFeature(e);
                return;
            }

            Point point = mapView.toMapPoint(new Point(e.getX(), e.getY()));

            // If we're creating a point, clear any existing point
            if (mEditMode == EditMode.POINT) {
                mPoints.clear();
            }

            // If a point is currently selected, move that point to tap point
            if (mMidPointSelected || mVertexSelected) {
                movePoint(point);
            } else {
                // If tap coincides with a mid-point, select that mid-point
                int idx1 = getSelectedIndex(e.getX(), e.getY(), mMidPoints, mapView);
                if (idx1 != -1) {
                    mMidPointSelected = true;
                    mInsertingIndex = idx1;
                } else {
                    // If tap coincides with a vertex, select that vertex
                    int idx2 = getSelectedIndex(e.getX(), e.getY(), mPoints, mapView);
                    if (idx2 != -1) {
                        mVertexSelected = true;
                        mInsertingIndex = idx2;
                    } else {
                        // No matching point above, add new vertex at tap point
                        mPoints.add(point);
                        mEditingStates.add(new EditingStates(mPoints, mMidPointSelected, mVertexSelected, mInsertingIndex));
                    }
                }
            }

            // Redraw the graphics layer
            refresh();
        }

        /**
         * Checks if a given location coincides (within a tolerance) with a point in a given array.
         *
         * @param x      Screen coordinate of location to check.
         * @param y      Screen coordinate of location to check.
         * @param points Array of points to check.
         * @param map    MapView containing the points.
         * @return Index within points of matching point, or -1 if none.
         */
        private int getSelectedIndex(double x, double y, ArrayList<Point> points, MapView map) {
            final int TOLERANCE = 40; // Tolerance in pixels

            if (points == null || points.size() == 0) {
                return -1;
            }

            // Find closest point
            int index = -1;
            double distSQ_Small = Double.MAX_VALUE;
            for (int i = 0; i < points.size(); i++) {
                Point p = map.toScreenPoint(points.get(i));
                double diffx = p.getX() - x;
                double diffy = p.getY() - y;
                double distSQ = diffx * diffx + diffy * diffy;
                if (distSQ < distSQ_Small) {
                    index = i;
                    distSQ_Small = distSQ;
                }
            }

            // Check if it's close enough
            if (distSQ_Small < (TOLERANCE * TOLERANCE)) {
                return index;
            }
            return -1;
        }

        /**
         * Moves the currently selected point to a given location.
         *
         * @param point Location to move the point to.
         */
        private void movePoint(Point point) {
            if (mMidPointSelected) {
                // Move mid-point to the new location and make it a vertex
                mPoints.add(mInsertingIndex + 1, point);
            } else {
                // Must be a vertex: move it to the new location
                ArrayList<Point> temp = new ArrayList<Point>();
                for (int i = 0; i < mPoints.size(); i++) {
                    if (i == mInsertingIndex) {
                        temp.add(point);
                    } else {
                        temp.add(mPoints.get(i));
                    }
                }
                mPoints.clear();
                mPoints.addAll(temp);
            }
            // Go back to the normal drawing mode and save the new editing state
            mMidPointSelected = false;
            mVertexSelected = false;
            mEditingStates.add(new EditingStates(mPoints, mMidPointSelected, mVertexSelected, mInsertingIndex));
        }

    }

    private void selectFeature(MotionEvent event) {
        if (selectedFeatureLayer != null) {
            selectedFeatureLayer.clearSelection();
        }

// convert event into screen click
        pointClicked = mMapView.toMapPoint(new Point(event.getX(), event.getY()));

        // build a query to select the clicked feature
        Query query = new Query();
        query.setOutFields(new String[]{"*"});
        query.setSpatialRelationship(SpatialRelationship.INTERSECTS);
        Geometry geometry = GeometryEngine.buffer(pointClicked, mMapView.getSpatialReference(), 20, null);
        query.setGeometry(geometry);
        query.setInSpatialReference(mMapView.getSpatialReference());

        // Loop on all the layers in the MapView
        Layer[] layers = mMapView.getLayers();
        for (Layer l : layers) {

            // Check if this is an ArcGISFeatureLayer
            if (l instanceof ArcGISFeatureLayer) {
                Log.d(TAG, l.getUrl());
                final ArcGISFeatureLayer featureLayer = (ArcGISFeatureLayer) l;

                // call the select features method and implement the callbacklistener
                featureLayer.selectFeatures(query, ArcGISFeatureLayer.SELECTION_METHOD.NEW, new CallbackListener<FeatureSet>() {

                    // handle any errors
                    public void onError(Throwable e) {
                        Log.d(TAG, "Select Features Error" + e.getLocalizedMessage());
                    }

                    public void onCallback(FeatureSet queryResults) {
                        if (selectFeatureSuccess) {
                            return;
                        }
                        if (queryResults.getGraphics().length > 0) {
                            selectFeatureSuccess = true;
                            Log.d(
                                    TAG,
                                    "Feature found id="
                                            + queryResults.getGraphics()[0].getAttributeValue(featureLayer.getObjectIdField()));

                            selectedFeatureLayer = featureLayer;
                            listAdapter = new AttributeListAdapter(getActivity(), selectedFeatureLayer.getFields(), selectedFeatureLayer.getTypes(),
                                    selectedFeatureLayer.getTypeIdField());
                            // set new data and notify adapter that data has changed
                            listAdapter.setFeatureSet(queryResults);
                            listAdapter.notifyDataSetChanged();

                            // This callback is not run in the main UI thread. All GUI
                            // related events must run in the UI thread,
                            // therefore use the Activity.runOnUiThread() method. See
                            // http://developer.android.com/reference/android/app/Activity.html#runOnUiThread(java.lang.Runnable)
                            // for more information.
                            getActivity().runOnUiThread(new Runnable() {

                                public void run() {

                                    // show the editor dialog.
                                    java.util.Random r = new java.util.Random();
                                    ATTRIBUTE_EDITOR_DIALOG_ID = r.nextInt();
                                    getActivity().showDialog(ATTRIBUTE_EDITOR_DIALOG_ID);

                                }
                            });

                        }
                    }
                });
            }
        }


    }

    /**
     * Overidden method from Activity class - this is the recommended way of creating dialogs
     */
    protected Dialog onCreateDialog(int id) {

        if (id == ATTRIBUTE_EDITOR_DIALOG_ID) {
            // create the attributes dialog
            Dialog dialog = new Dialog(getActivity());
            listLayout = inflator.inflate(R.layout.list_layout, null);
            listView = (ListView) listLayout.findViewById(R.id.list_view);
            listView.setAdapter(listAdapter);
            dialog.setContentView(listLayout);
            dialog.setTitle("????????????");

            // set button on click listeners, setting as XML attributes doesn't work
            // due to a scope/thread issue
            Button btnEditCancel = (Button) listLayout.findViewById(R.id.btn_edit_discard);
            btnEditCancel.setOnClickListener(returnOnClickDiscardChangesListener());

            Button btnEditApply = (Button) listLayout.findViewById(R.id.btn_edit_apply);
            btnEditApply.setOnClickListener(returnOnClickApplyChangesListener());

            return dialog;
        }
        return null;
    }

    /**
     * Helper method to return an OnClickListener for the Apply button
     */
    private View.OnClickListener returnOnClickApplyChangesListener() {

        return new View.OnClickListener() {

            public void onClick(View v) {
                if (mEditMode != EditMode.NONE) {
                    applySave();
                    return;
                }
                boolean isTypeField = false;
                boolean hasEdits = false;
                boolean updateMapLayer = false;
                Map<String, Object> attrs = new HashMap<>();

                // loop through each attribute and set the new values if they have
                // changed
                for (int i = 0; i < listAdapter.getCount(); i++) {

                    AttributeItem item = (AttributeItem) listAdapter.getItem(i);
                    String value;

                    // check to see if the View has been set
                    if (item.getView() != null) {

                        // determine field type and therefore View type
                        if (item.getField().getName().equals(selectedFeatureLayer.getTypeIdField())) {
                            // drop down spinner

                            Spinner spinner = (Spinner) item.getView();
                            // get value for the type
                            String typeName = spinner.getSelectedItem().toString();
                            value = FeatureLayerUtils.returnTypeIdFromTypeName(selectedFeatureLayer.getTypes(), typeName);

                            // update map layer as for this featurelayer the type change will
                            // change the features symbol.
                            isTypeField = true;

                        } else if (FeatureLayerUtils.FieldType.determineFieldType(item.getField()) == FeatureLayerUtils.FieldType.DATE) {
                            // date

                            Button dateButton = (Button) item.getView();
                            value = dateButton.getText().toString();

                        } else {
                            // edit text

                            EditText editText = (EditText) item.getView();
                            value = editText.getText().toString();

                        }

                        // try to set the attribute value on the graphic and see if it has
                        // been changed
                        boolean hasChanged = FeatureLayerUtils.setAttribute(attrs, listAdapter.featureSet.getGraphics()[0],
                                item.getField(), value, listAdapter.formatter);

                        // if a value has for this field, log this and set the hasEdits
                        // boolean to true
                        if (hasChanged) {

                            Log.d(TAG, "Change found for field=" + item.getField().getName() + " value = " + value
                                    + " applyEdits() will be called");
                            hasEdits = true;

                            // If the change was from a Type field then set the dynamic map
                            // service to update when the edits have been applied, as the
                            // renderer of the feature will likely change
                            if (isTypeField) {

                                updateMapLayer = true;

                            }
                        }

                        // check if this was a type field, if so set boolean back to false
                        // for next field
                        if (isTypeField) {

                            isTypeField = false;
                        }
                    }
                }

                // check there have been some edits before applying the changes
                if (hasEdits) {

                    // set objectID field value from graphic held in the featureset
                    attrs.put(selectedFeatureLayer.getObjectIdField(), listAdapter.featureSet.getGraphics()[0].getAttributeValue(selectedFeatureLayer.getObjectIdField()));
                    Graphic newGraphic = new Graphic(null, null, attrs);
                    selectedFeatureLayer.applyEdits(null, null, new Graphic[]{newGraphic}, createEditCallbackListener(updateMapLayer));
                }

                selectFeatureSuccess = false;
                // close the dialog
                getActivity().dismissDialog(ATTRIBUTE_EDITOR_DIALOG_ID);

            }
        };

    }

    /**
     * OnClick method for the Discard button
     */
    private View.OnClickListener returnOnClickDiscardChangesListener() {

        return new View.OnClickListener() {

            public void onClick(View v) {
                selectFeatureSuccess = false;
                // close the dialog
                getActivity().dismissDialog(ATTRIBUTE_EDITOR_DIALOG_ID);

            }
        };

    }

    /**
     * Helper method to create a CallbackListener<FeatureEditResult[][]>
     *
     * @return CallbackListener<FeatureEditResult[][]>
     */
    private CallbackListener<FeatureEditResult[][]> createEditCallbackListener(final boolean updateLayer) {

        return new CallbackListener<FeatureEditResult[][]>() {

            public void onCallback(FeatureEditResult[][] result) {

                // check the response for success or failure
                if (result[2] != null && result[2][0] != null && result[2][0].isSuccess()) {

                    // see if we want to update the dynamic layer to get new symbols for
                    // updated features
                    if (updateLayer) {

                        selectedFeatureLayer.refresh();

                    }
                }
            }

            public void onError(Throwable e) {

            }
        };
    }

    /**
     * This class provides the adapter for the list of feature types.
     *//*
    class FeatureTypeListAdapter extends ArrayAdapter<FeatureTypeData> {

        public FeatureTypeListAdapter(Context context, ArrayList<FeatureTypeData> featureTypes) {
            super(context, 0, featureTypes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            FeatureTypeViewHolder holder = null;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listitem, null);
                holder = new FeatureTypeViewHolder();
                holder.imageView = (ImageView) view.findViewById(R.id.icon);
                holder.textView = (TextView) view.findViewById(R.id.label);
            } else {
                holder = (FeatureTypeViewHolder) view.getTag();
            }

            FeatureTypeData featureType = getItem(position);
            holder.imageView.setImageBitmap(featureType.getBitmap());
            holder.textView.setText(mFeatureTypeList.get(position).getName());
            view.setTag(holder);
            return view;
        }

    }

    *//**
     * Holds data related to an item in the list of feature types.
     *//*
    class FeatureTypeViewHolder {
        ImageView imageView;

        TextView textView;
    }*/

}
