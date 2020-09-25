package com.mohsin.huaweinew;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.huawei.hihealth.error.HiHealthError;
import com.huawei.hihealth.listener.ResultCallback;
import com.huawei.hihealthkit.HiHealthDataQuery;
import com.huawei.hihealthkit.HiHealthDataQueryOption;
import com.huawei.hihealthkit.auth.HiHealthAuth;
import com.huawei.hihealthkit.auth.HiHealthOpenPermissionType;
import com.huawei.hihealthkit.auth.IAuthorizationListener;
import com.huawei.hihealthkit.data.HiHealthData;
import com.huawei.hihealthkit.data.HiHealthPointData;
import com.huawei.hihealthkit.data.HiHealthSetData;
import com.huawei.hihealthkit.data.store.HiHealthDataStore;
import com.huawei.hihealthkit.data.store.HiRealTimeListener;
import com.huawei.hihealthkit.data.store.HiSportDataCallback;
import com.huawei.hihealthkit.data.type.HiHealthPointType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "DemoActivity";

    private static final int DEFAULT_MAP_SIZE = 16;

    private static final int DEFAULT_LIST_SIZE = 16;

    private static final int DEFAULT_STRING_BUFFER_SIZE = 16;

    private static final int REQUEST_AUTHORIZATION = 0;

    private static final int PERMISSION_STATUS = 1;

    private static final int GET_GENDER = 2;

    private static final int GET_BIRTHDAY = 3;

    private static final int GET_HEIGHT = 4;

    private static final int GET_WEIGHT = 5;

    private static final int EXEC_QUERY = 6;

    private static final int GET_COUNT = 7;

    private static final int SAVE_SAMPLE = 8;

    private static final int SAVE_SAMPLES = 9;

    private static final int DELETE_SAMPLE = 10;

    private static final int DELETE_SAMPLES = 11;

    private static final int START_READING_HEARTRATE = 12;

    private static final int STOP_READING_HEARTRATE = 13;

    private static final int START_READING_RRI = 14;

    private static final int STOP_READING_RRI = 15;

    private static final int START_REALTIME_SPORT = 16;

    private static final int STOP_REALTIME_SPORT = 17;

    private Button buttonRequestAuthorization;

    private Button buttonGetPermissionStatus;

    private Button buttonGetGender;

    private Button buttonGetBirthday;

    private Button buttonGetHeight;

    private Button buttonGetWeight;

    private Button buttonExecQuery;

    private Button buttonGetCount;

    private Button buttonSaveSample;

    private Button buttonSaveSamples;

    private Button buttonDeleteSample;

    private Button buttonDeleteSamples;

    private Button buttonStartReadingHeartRate;

    private Button buttonStopReadingHeartRate;

    private Button buttonStartReadingRri;

    private Button buttonStopReadingRri;

    private Button buttonStartRealTimeSportData;

    private Button buttonStopRealTimeSportData;

    private TextView dataText;
    public Handler demoHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String resultData = String.valueOf(msg.obj);
            switch (msg.what) {
                case REQUEST_AUTHORIZATION:
                    dataText.setText("requestAuthorization:" + resultData);
                    break;
                case PERMISSION_STATUS:
                    dataText.setText("getPermissionStatus:" + resultData);
                    break;
                case GET_GENDER:
                    dataText.setText("getGender:" + resultData);
                    break;
                case GET_BIRTHDAY:
                    dataText.setText("getBirthday:" + resultData);
                    break;
                case GET_HEIGHT:
                    dataText.setText("getHeight:" + resultData);
                    break;
                case GET_WEIGHT:
                    dataText.setText("getWeight:" + resultData);
                    break;
                case EXEC_QUERY:
                    dataText.setText("execQuery:" + resultData);
                    break;
                case GET_COUNT:
                    dataText.setText("getCount:" + resultData);
                    break;
                case SAVE_SAMPLE:
                    dataText.setText("saveSample:" + resultData);
                    break;
                case SAVE_SAMPLES:
                    dataText.setText("saveSamples:" + resultData);
                    break;
                case DELETE_SAMPLE:
                    dataText.setText("deleteSample:" + resultData);
                    break;
                case DELETE_SAMPLES:
                    dataText.setText("deleteSamples:" + resultData);
                    break;
                case START_READING_HEARTRATE:
                    dataText.setText("startReadingHeartRate:" + resultData);
                    break;
                case STOP_READING_HEARTRATE:
                    dataText.setText("stopReadingHeartRate:" + resultData);
                    break;
                case START_READING_RRI:
                    dataText.setText("startReadingRri:" + resultData);
                    break;
                case STOP_READING_RRI:
                    dataText.setText("stopReadingRri:" + resultData);
                    break;
                case START_REALTIME_SPORT:
                    dataText.setText("startRealTimeSportData:" + resultData);
                    break;
                case STOP_REALTIME_SPORT:
                    dataText.setText("stopRealTimeSportData:" + resultData);
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataText = findViewById(R.id.dataText);
        initView();
    }
    private void initView() {
        buttonRequestAuthorization = findViewById(R.id.requestAuthorization);
        buttonRequestAuthorization.setOnClickListener(this);

        buttonGetPermissionStatus = findViewById(R.id.getPermissionStatus);
        buttonGetPermissionStatus.setOnClickListener(this);

        buttonGetGender = findViewById(R.id.getGender);
        buttonGetGender.setOnClickListener(this);

        buttonGetBirthday = findViewById(R.id.getBirthday);
        buttonGetBirthday.setOnClickListener(this);

        buttonGetHeight = findViewById(R.id.getHeight);
        buttonGetHeight.setOnClickListener(this);

        buttonGetWeight = findViewById(R.id.getWeight);
        buttonGetWeight.setOnClickListener(this);

        buttonExecQuery = findViewById(R.id.execQuery);
        buttonExecQuery.setOnClickListener(this);

        buttonGetCount = findViewById(R.id.getCount);
        buttonGetCount.setOnClickListener(this);

        buttonSaveSample = findViewById(R.id.saveSample);
        buttonSaveSample.setOnClickListener(this);

        buttonSaveSamples = findViewById(R.id.saveSamples);
        buttonSaveSamples.setOnClickListener(this);

        buttonDeleteSample = findViewById(R.id.deleteSample);
        buttonDeleteSample.setOnClickListener(this);

        buttonDeleteSamples = findViewById(R.id.deleteSamples);
        buttonDeleteSamples.setOnClickListener(this);

        buttonStartReadingHeartRate = findViewById(R.id.startReadingHeartRate);
        buttonStartReadingHeartRate.setOnClickListener(this);

        buttonStopReadingHeartRate = findViewById(R.id.stopReadingHeartRate);
        buttonStopReadingHeartRate.setOnClickListener(this);

        buttonStartReadingRri = findViewById(R.id.startReadingRri);
        buttonStartReadingRri.setOnClickListener(this);

        buttonStopReadingRri = findViewById(R.id.stopReadingRri);
        buttonStopReadingRri.setOnClickListener(this);

        buttonStartRealTimeSportData = findViewById(R.id.startRealTimeSportData);
        buttonStartRealTimeSportData.setOnClickListener(this);

        buttonStopRealTimeSportData = findViewById(R.id.stopRealTimeSportData);
        buttonStopRealTimeSportData.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.requestAuthorization:
                doRequestAuthorization(MainActivity.this);
                break;
            case R.id.getPermissionStatus:
                doGetDataAuthStatus(MainActivity.this);
                break;
            case R.id.getGender:
                doGetGender(MainActivity.this);
                break;
            case R.id.getBirthday:
                doGetBirthday(MainActivity.this);
                break;
            case R.id.getHeight:
                doGetHeight(MainActivity.this);
                break;
            case R.id.getWeight:
                doGetWeight(MainActivity.this);
                break;
            case R.id.execQuery:
                doExecQuery(MainActivity.this);
                break;
            case R.id.getCount:
                doGetCount(MainActivity.this);
                break;
            case R.id.saveSample:
                doSaveSample(MainActivity.this);
                break;
            case R.id.saveSamples:
                doSaveSamples(MainActivity.this);
                break;
            case R.id.deleteSample:
                doDeleteSample(MainActivity.this);
                break;
            case R.id.deleteSamples:
                doDeleteSamples(MainActivity.this);
                break;
            case R.id.startReadingHeartRate:
                doStartReadingHeartRate(MainActivity.this);
                break;
            case R.id.stopReadingHeartRate:
                doStopReadingHeartRate(MainActivity.this);
                break;
            case R.id.startReadingRri:
                doStartReadingRri(MainActivity.this);
                break;
            case R.id.stopReadingRri:
                doStopReadingRri(MainActivity.this);
                break;
            case R.id.startRealTimeSportData:
                doStartRealtimeSportData(MainActivity.this);
                break;
            case R.id.stopRealTimeSportData:
                doStopRealtimeSportData(MainActivity.this);
                break;
            default:
                break;
        }
    }

    /**
     * Developer Alliance Code:requestAuthorization
     *
     * @param context context
     */
    private void doRequestAuthorization(Context context) {
        int[] read = new int[] {
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_USER_PROFILE_INFORMATION,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_USER_PROFILE_FEATURE,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_HEART,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_WALK_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_RUN_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_RIDE_METADATA,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_WEIGHT,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_SET_CORE_SLEEP,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_STEP_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_DISTANCE_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_INTENSITY,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_POINT_CALORIES_SUM,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_REALTIME_HEARTRATE,
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_READ_DATA_REAL_TIME_SPORT
        };
        int[] write = new int[] {HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT};
        HiHealthAuth.requestAuthorization(context, write, read, new IAuthorizationListener() {
            @Override
            public void onResult(int resultCode, Object resultDesc) {
                Log.i(TAG, "requestAuthorization onResult:" + resultCode);
                Message message = Message.obtain();
                message.what = REQUEST_AUTHORIZATION;
                message.obj = resultDesc;
                demoHandler.sendMessage(message);
                switch (resultCode) {
                    case HiHealthError.SUCCESS:
                        Log.i(TAG, "Authorization page is shown foreground.");
                        break;
                    case HiHealthError.FAILED:
                        Log.i(TAG, "AIDL connection failed.");
                        break;
                    case HiHealthError.ERR_SCOPE_EXCEPTION:
                        Log.i(TAG, "Contact developer website for more info.");
                        break;
                    case HiHealthError.ERR_API_EXECEPTION:
                        Log.i(TAG, "Try update Health app or try later.");
                        break;
                    default:
                        Log.i(TAG, "Please contact HiHealth team if you catch this");
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getDataAuthStatus
     *
     * @param context context
     */
    private void doGetDataAuthStatus(Context context) {
        int write = HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT;

        HiHealthAuth.getDataAuthStatus(context, write, new IAuthorizationListener() {
            @Override
            public void onResult(int resultCode, Object permission) {
                Log.i(TAG, "doGetDataAuthStatus onResult:" + resultCode);
                if (resultCode != HiHealthError.SUCCESS) {
                    Log.i(TAG, "Check more log to see why it failed");
                    Message message = Message.obtain();
                    message.what = PERMISSION_STATUS;
                    message.obj = resultCode;
                    demoHandler.sendMessage(message);
                    return;
                }
                List<Integer> list = (List) permission;
                Message message = Message.obtain();
                message.what = PERMISSION_STATUS;
                message.obj = list;
                demoHandler.sendMessage(message);
                int status = list.get(0);
                Log.i(TAG, "permission = " + status);
                switch (status) {
                    case 0:
                        Log.i(TAG, "call requestAuthorization() first");
                        break;
                    case 1:
                        Log.i(TAG, "Yeah! Got permission. Try saveSample() next!");
                        break;
                    case 2:
                        Log.i(TAG, "oh, it's not granted");
                        break;
                    default:
                        Log.i(TAG, "Please contact HiHealth team if you catch this");
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getGender
     *
     * @param context context
     */
    private void doGetGender(Context context) {
        HiHealthDataStore.getGender(context, new ResultCallback() {
            @Override
            public void onResult(int errorCode, Object gender) {
                Log.i(TAG, "call doGetGender() resultCode is " + errorCode);
                Log.i(TAG, "call doGetGender() gender is " + gender);
                if (gender != null) {
                    Message message = Message.obtain();
                    message.what = GET_GENDER;
                    message.obj = gender;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getBirthday
     *
     * @param context context
     */
    private void doGetBirthday(Context context) {
        HiHealthDataStore.getBirthday(context, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object birthday) {
                Log.i(TAG, "call doGetBirthday() resultCode is " + resultCode);
                Log.i(TAG, "call doGetBirthday() birthday is " + birthday);
                if (birthday != null) {
                    // For example, "1978-05-20" would return 19780520
                    Message message = Message.obtain();
                    message.what = GET_BIRTHDAY;
                    message.obj = birthday;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getHeight
     *
     * @param context context
     */
    private void doGetHeight(Context context) {
        HiHealthDataStore.getHeight(context, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object height) {
                Log.i(TAG, "call doGetHeight() resultCode is " + resultCode);
                Log.i(TAG, "call doGetHeight() height is " + height);
                if (height != null) {
                    Message message = Message.obtain();
                    message.what = GET_HEIGHT;
                    message.obj = height;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getWeight
     *
     * @param context context
     */
    private void doGetWeight(Context context) {
        HiHealthDataStore.getWeight(context, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object weight) {
                Log.i(TAG, "call doGetWeight() resultCode is " + resultCode);
                Log.i(TAG, "call doGetWeight() weight is " + weight);
                if (weight != null) {
                    Message message = Message.obtain();
                    message.what = GET_WEIGHT;
                    message.obj = weight;
                    demoHandler.sendMessage(message);
                } else {
                    Message message = Message.obtain();
                    message.what = GET_WEIGHT;
                    message.obj = resultCode;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:execQuery
     *
     * @param context context
     */
    private void doExecQuery(Context context) {
        int timeout = 0;
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 1000L * 60 * 60 * 24; // Check Data of the latest 1 days

        // Get HiHealthPointType, like steps, distance, calories, exercise intensity per day.
        // Statistic data returned as an ArrayList where each element represents the value of one day
        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_STEP_SUM, startTime,
                endTime, new HiHealthDataQueryOption());
        Log.i(TAG, "sampleType = " + hiHealthDataQuery.getSampleType());
        HiHealthDataStore.execQuery(context, hiHealthDataQuery, timeout, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "enter query onSuccess");
                Log.i(TAG, "call doExecQuery() resultCode is " + resultCode);
                Log.i(TAG, "call doExecQuery() data is " + data);
                if (data != null) {
                    Log.i(TAG, "enter query not null");
                    List<HiHealthPointData> dataList = (ArrayList) data;
                    StringBuffer stringBuffer = new StringBuffer(DEFAULT_STRING_BUFFER_SIZE);
                    for (Object obj : dataList) {
                        stringBuffer.append("type")
                                .append(((HiHealthPointData) obj).getType())
                                .append(":");
                        stringBuffer.append("value")
                                .append(((HiHealthPointData) obj).getValue())
                                .append(" ");
                    }
                    Message message = Message.obtain();
                    message.what = EXEC_QUERY;
                    message.obj = stringBuffer;
                    demoHandler.sendMessage(message);
                    Log.i(TAG, "resultCode is " + resultCode + " data: " + stringBuffer.toString());
                } else {
                    Log.i(TAG, "User has no data these days or cancel authorization.");
                    Message message = Message.obtain();
                    message.what = EXEC_QUERY;
                    message.obj = resultCode;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:getCount
     *
     * @param context context
     */
    private void doGetCount(final Context context) {
        final int maxCount = 500;
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - 1000L * 60 * 60 * 24 * 5; // Check Data of the latest 5 days
        HiHealthDataQuery hiHealthDataQuery = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_STEP_SUM, startTime,
                endTime, new HiHealthDataQueryOption());
        Log.i(TAG, "sampletype = " + hiHealthDataQuery.getSampleType());
        HiHealthDataStore.getCount(context, hiHealthDataQuery, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "call doGetCount() resultCode is " + resultCode);
                Log.i(TAG, "call doGetCount() data is " + data);
                if (data != null) {
                    int count = (int) data;
                    Message message = Message.obtain();
                    message.what = GET_COUNT;
                    message.obj = count;
                    demoHandler.sendMessage(message);
                    if (count > maxCount) {
                        Log.i(TAG, "call execQuery() with a shorten duration.");
                        long newEndTime = startTime + (endTime - startTime) / 2;
                        final HiHealthDataQuery query = new HiHealthDataQuery(HiHealthPointType.DATA_POINT_STEP_SUM,
                                startTime, newEndTime, new HiHealthDataQueryOption());
                        HiHealthDataStore.execQuery(context, query, 0, new ResultCallback() {
                            @Override
                            public void onResult(int resultCode, Object data) {
                                Log.i(TAG, "call execQuery() resultCode is " + resultCode);
                            }
                        });
                    }
                } else {
                    Message message = Message.obtain();
                    message.what = GET_COUNT;
                    message.obj = resultCode;
                    demoHandler.sendMessage(message);
                }
            }
        });
    }

    /**
     * Developer Alliance Code:saveSample
     *
     * @param context context
     */
    private void doSaveSample(Context context) {
        Map map = new HashMap(DEFAULT_MAP_SIZE);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT, 70.5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMI, 18.8D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MUSCLES, 33.5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMR, 4D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURE, 5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_FATLEVEL, 6D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BONE_MINERAL, 7D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_PROTEIN, 8D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYSCORE, 9D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYAGE, 10D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYFAT, 11D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_IMPEDANCE, 12D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURERATE, 13D);
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - 1000L * 60 * 60 * 24 * 5; // Check Data of the latest 5 days
        HiHealthSetData hiHealthSetData = new HiHealthSetData(
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT, map, startTime, endTime);
        HiHealthDataStore.saveSample(context, hiHealthSetData, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "call doSaveSample() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = SAVE_SAMPLE;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:saveSamples
     *
     * @param context context
     */
    private void doSaveSamples(Context context) {
        Map map = new HashMap(DEFAULT_MAP_SIZE);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT, 70.5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMI, 18.8D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MUSCLES, 33.5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BMR, 4D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURE, 5D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_FATLEVEL, 6D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BONE_MINERAL, 7D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_PROTEIN, 8D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYSCORE, 9D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYAGE, 10D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_BODYFAT, 11D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_IMPEDANCE, 12D);
        map.put(HiHealthPointType.DATA_POINT_WEIGHT_MOISTURERATE, 13D);
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - 1000L * 60 * 60 * 24 * 5; // Check Data of the latest 5 days
        List<HiHealthData> hiHealthDataList = new ArrayList<>(DEFAULT_LIST_SIZE);
        HiHealthSetData hiHealthSetData = new HiHealthSetData(
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT, map, startTime, endTime);
        hiHealthDataList.add(hiHealthSetData);
        HiHealthDataStore.saveSamples(context, hiHealthDataList, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "call doSaveSamples() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = SAVE_SAMPLES;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:deleteSample
     *
     * @param context context
     */
    private void doDeleteSample(Context context) {
        Map map = new HashMap(DEFAULT_MAP_SIZE);
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - 1000L * 60 * 60 * 24 * 5; // Check Data of the latest 5 days
        HiHealthSetData hiHealthSetData = new HiHealthSetData(
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT, map, startTime, endTime);
        HiHealthDataStore.deleteSample(context, hiHealthSetData, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "call doDeleteSample() resultCode is " + resultCode);
                Log.i(TAG, "call doDeleteSample() data is " + data);
                Message message = Message.obtain();
                message.what = DELETE_SAMPLE;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:deleteSamples
     *
     * @param context context
     */
    private void doDeleteSamples(Context context) {
        Map map = new HashMap(DEFAULT_MAP_SIZE);
        final long endTime = System.currentTimeMillis();
        final long startTime = endTime - 1000L * 60 * 60 * 24 * 5; // Check Data of the latest 5 days
        List<HiHealthData> hiHealthDataList = new ArrayList<>(DEFAULT_LIST_SIZE);
        HiHealthSetData hiHealthSetData = new HiHealthSetData(
                HiHealthOpenPermissionType.HEALTH_OPEN_PERMISSION_TYPE_WRITE_DATA_SET_WEIGHT, map, startTime, endTime);
        hiHealthDataList.add(hiHealthSetData);
        HiHealthDataStore.deleteSamples(context, hiHealthDataList, new ResultCallback() {
            @Override
            public void onResult(int resultCode, Object data) {
                Log.i(TAG, "call doDeleteSample() resultCode is " + resultCode);
                Log.i(TAG, "call doDeleteSample() data is " + data);
                Message message = Message.obtain();
                message.what = DELETE_SAMPLES;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:startReadingHeartRate
     *
     * @param context context
     */
    private void doStartReadingHeartRate(Context context) {
        HiHealthDataStore.startReadingHeartRate(context, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStartReadingHeartRate() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = START_READING_HEARTRATE;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(TAG, "call doStartReadingHeartRate() resultCode is " + resultCode);
                Log.i(TAG, "call doStartReadingHeartRate() value is " + data);
                Message message = Message.obtain();
                message.what = START_READING_HEARTRATE;
                message.obj = data;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:stopReadingHeartRate
     *
     * @param context context
     */
    private void doStopReadingHeartRate(Context context) {
        HiHealthDataStore.stopReadingHeartRate(context, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStopReadingHeartRate() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = STOP_READING_HEARTRATE;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(TAG, "call doStopReadingHeartRate() resultCode is " + resultCode);
                Log.i(TAG, "call doStopReadingHeartRate() value is " + data);
                Message message = Message.obtain();
                message.what = STOP_READING_HEARTRATE;
                message.obj = data;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:startReadingRri
     *
     * @param context context
     */
    private void doStartReadingRri(Context context) {
        HiHealthDataStore.startReadingRri(context, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStartReadingRri() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = START_READING_RRI;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(TAG, "call doStartReadingRri() resultCode is " + resultCode);
                Log.i(TAG, "call doStartReadingRri() value is " + data);
                Message message = Message.obtain();
                message.what = START_READING_RRI;
                message.obj = data;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:stopReadingRri
     *
     * @param context context
     */
    private void doStopReadingRri(Context context) {
        HiHealthDataStore.stopReadingRri(context, new HiRealTimeListener() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStopReadingRri() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = STOP_READING_RRI;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onChange(int resultCode, String data) {
                Log.i(TAG, "call doStopReadingRri() resultCode is " + resultCode);
                Log.i(TAG, "call doStopReadingRri() value is " + data);
                Message message = Message.obtain();
                message.what = STOP_READING_RRI;
                message.obj = data;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:startRealtimeSportData
     *
     * @param context context
     */
    private void doStartRealtimeSportData(Context context) {
        HiHealthDataStore.startRealTimeSportData(context, new HiSportDataCallback() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStartRealtimeSportData() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = START_REALTIME_SPORT;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onDataChanged(int sportState, Bundle bundle) {
                Log.i(TAG, "call doStartRealtimeSportData() sportState is " + sportState);
                Log.i(TAG, "call doStartRealtimeSportData() bundle is " + bundle);
                Message message = Message.obtain();
                message.what = START_REALTIME_SPORT;
                message.obj = bundle;
                demoHandler.sendMessage(message);
            }
        });
    }

    /**
     * Developer Alliance Code:stopRealtimeSportData
     *
     * @param context context
     */
    private void doStopRealtimeSportData(Context context) {
        HiHealthDataStore.stopRealTimeSportData(context, new HiSportDataCallback() {
            @Override
            public void onResult(int resultCode) {
                Log.i(TAG, "call doStopRealtimeSportData() resultCode is " + resultCode);
                Message message = Message.obtain();
                message.what = STOP_REALTIME_SPORT;
                message.obj = resultCode;
                demoHandler.sendMessage(message);
            }

            @Override
            public void onDataChanged(int sportState, Bundle bundle) {
                Log.i(TAG, "call doStopRealtimeSportData() sportState is " + sportState);
                Log.i(TAG, "call doStopRealtimeSportData() bundle is " + bundle);
                Message message = Message.obtain();
                message.what = STOP_REALTIME_SPORT;
                message.obj = bundle;
                demoHandler.sendMessage(message);
            }
        });
    }
}
