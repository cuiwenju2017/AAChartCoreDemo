package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.test.AAChartCoreLib.AAChartCreator.AAChartModel;
import com.example.test.AAChartCoreLib.AAChartCreator.AAChartView;
import com.example.test.AAChartCoreLib.AAChartCreator.AAMoveOverEventMessageModel;
import com.example.test.AAChartCoreLib.AAChartCreator.AASeriesElement;
import com.example.test.AAChartCoreLib.AAChartEnum.AAChartSymbolStyleType;
import com.example.test.AAChartCoreLib.AAChartEnum.AAChartSymbolType;
import com.example.test.AAChartCoreLib.AAChartEnum.AAChartType;
import com.example.test.AAChartCoreLib.AAChartEnum.AAChartZoomType;
import com.example.test.AAChartCoreLib.AAOptionsModel.AAStyle;
import com.google.gson.Gson;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements AAChartView.AAChartViewCallBack {

    private AAChartModel aaChartModel;
    private AAChartView aaChartView;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpAAChartView();
        repeatUpdateChartData();//更新数据刷新图表
    }

    private void setUpAAChartView() {
        aaChartView = findViewById(R.id.AAChartView);
        aaChartView.callBack = this;
        aaChartModel = configureAAChartModel();
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

    AAChartModel configureAAChartModel() {
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Area)//图标类型
                .title("编程语言的热度")//标题
                .titleStyle(AAStyle.style("#ffffff", 16f))//标题文本风格样式
                .subtitle("虚拟数据")//副标题
                .subtitleStyle(AAStyle.style("#ffffff", 10f))////副标题文本风格样式
                .backgroundColor("#3700B3")//背景颜色
                .categories(new String[]{"Java", "Swift", "Python", "Ruby", "PHP", "Go", "C", "C#", "C++"})
                .dataLabelsEnabled(false)//是否显示坐标上的数据
                .axesTextColor("#ffffff")//x 轴和 y 轴文字颜色
                .xAxisGridLineWidth(0.5f)//x轴线的宽度
                .yAxisGridLineWidth(0.5f)//y轴线的宽度
                .markerRadius(5f)//折线连接点的半径长度
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)//折线曲线连接点的自定义风格样式
                .markerSymbol(AAChartSymbolType.Circle)//坐标点类型,不写默认每个不同
                .zoomType(AAChartZoomType.XY)//手势缩放
                .tooltipValueSuffix("%")//浮动提示框单位后缀
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("东京")
                                .fillOpacity(0.3f)//折线填充图、曲线填充图、直方折线填充图等填充图类型的填充颜色透明度
                                .data(new Object[]{7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3}),
                        new AASeriesElement()
                                .name("纽约")
                                .fillOpacity(0.3f)
                                .data(new Object[]{0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1}),
                        new AASeriesElement()
                                .name("伦敦")
                                .fillOpacity(0.3f)
                                .data(new Object[]{0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3}),
                        new AASeriesElement()
                                .name("柏林")
                                .fillOpacity(0.3f)
                                .data(new Object[]{3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2})
                });
        this.aaChartModel = aaChartModel;
        return aaChartModel;
    }

    private AASeriesElement[] configureChartSeriesArray() {
        int maxRange = 9;//x轴最大数量

        Object[] numberArr1 = new Object[maxRange];
        Object[] numberArr2 = new Object[maxRange];
        Object[] numberArr3 = new Object[maxRange];
        Object[] numberArr4 = new Object[maxRange];

        for (int i = 0; i < maxRange; i++) {
            double numb1 = (Math.random() * 100);//0到100的随机数
            double numb2 = (Math.random() * 100);//0到100的随机数
            double numb3 = (Math.random() * 100);//0到100的随机数
            double numb4 = (Math.random() * 100);//0到100的随机数
            DecimalFormat decimalFormat = new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
            double p1 = Double.parseDouble(decimalFormat.format(numb1));
            double p2 = Double.parseDouble(decimalFormat.format(numb2));
            double p3 = Double.parseDouble(decimalFormat.format(numb3));
            double p4 = Double.parseDouble(decimalFormat.format(numb4));

            numberArr1[i] = p1;
            numberArr2[i] = p2;
            numberArr3[i] = p3;
            numberArr4[i] = p4;
        }

        AASeriesElement[] aaSeriesElementsArr = new AASeriesElement[]{
                new AASeriesElement()
                        .name("东京")
                        .fillOpacity(0.3f)//折线填充图、曲线填充图、直方折线填充图等填充图类型的填充颜色透明度
                        .data(numberArr1),
                new AASeriesElement()
                        .name("纽约")
                        .fillOpacity(0.3f)
                        .data(numberArr2),
                new AASeriesElement()
                        .name("伦敦")
                        .fillOpacity(0.3f)
                        .data(numberArr3),
                new AASeriesElement()
                        .name("柏林")
                        .fillOpacity(0.3f)
                        .data(numberArr4)
        };

        return aaSeriesElementsArr;
    }

    private void repeatUpdateChartData() {
        final Handler mHandler = new Handler();
        Runnable r = new Runnable() {

            @Override
            public void run() {
                AASeriesElement[] aaSeriesElementsArr = configureChartSeriesArray();

                aaChartView.aa_onlyRefreshTheChartDataWithChartOptionsSeriesArray(aaSeriesElementsArr);

                //每隔n秒循环执行run方法
                mHandler.postDelayed(this, 5000);
            }
        };

        mHandler.postDelayed(r, 2000);//延时2000毫秒
    }

    @Override
    public void chartViewDidFinishLoad(AAChartView aaChartView) {
        Log.i(TAG, "chartViewDidFinishLoad: " + "🔥🔥🔥🔥🔥图表加载完成回调方法!!!!!!!! ");
    }

    @Override
    public void chartViewMoveOverEventMessage(AAChartView aaChartView, AAMoveOverEventMessageModel messageModel) {
        Gson gson = new Gson();
        Log.i(TAG, "chartViewMoveOverEventMessage: " + "👌👌👌👌👌move over event message " + gson.toJson(messageModel));
    }
}