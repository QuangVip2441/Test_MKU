package com.example.test_mku.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test_mku.R;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExportPDFFragment extends Fragment {

    private MaterialButton buttonExportPdf;
    public ExportPDFFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export_p_d_f, container, false);
        buttonExportPdf = view.findViewById(R.id.buttonExportPdf);
        buttonExportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {exportPdf();}
        });
        return view;
    }

    private void exportPdf() {
        PdfDocument pdf = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(400, 600, 1).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);

        int height = pageInfo.getPageHeight();
        int width = pageInfo.getPageWidth();
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        Typeface typePlain = getResources().getFont(R.font.times);
        Typeface typeBold = getResources().getFont(R.font.timesbd);


        paint.setColor(Color.BLACK);
        paint.setTextSize(8);

        paint.setTextAlign(Paint.Align.CENTER);
        String[] header = {
                "TRƯỜNG ĐẠI HỌC CỬU LONG",
                "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM",
                "TRUNG TÂM CÔNG NGHỆ THÔNG TIN - ĐIỆN TỬ",
                "Độc lập - Tự do - Hạnh phúc"
        };
        int firstLine = 40;
        int spacing = 12;
        int indent = 20;


        paint.setTypeface(typePlain);
        canvas.drawText(header[0], width / 4, firstLine, paint);

        paint.setTypeface(typeBold);
        canvas.drawText(header[1], 3 * width / 4, firstLine, paint);

        canvas.drawText(header[2], width / 4, firstLine + spacing, paint);
        canvas.drawLine(width / 4 - paint.measureText(header[3]) / 2, firstLine + spacing + 3, width / 4 + paint.measureText(header[3]) / 2, firstLine + spacing + 3, paint);

        canvas.drawText(header[3], 3 * width / 4, firstLine + spacing, paint);
        canvas.drawLine(3 * width / 4 - paint.measureText(header[3]) / 2, firstLine + spacing + 3, 3 * width / 4 + paint.measureText(header[3]) / 2, firstLine + spacing + 3, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(10);
        paint.setTypeface(typeBold);
        canvas.drawText("KẾT QUẢ THI TRẮC NGHIỆM", width / 2, firstLine + 4 * spacing, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Thông tin thí sinh", indent, firstLine + 6 * spacing, paint);

        // Table
        paint.setTextSize(8);
        paint.setTypeface(typePlain);
        String[] firstColumns = { "Số báo danh:", "Họ tên:", "Ngày sinh:", "Điện thoại:", "Email:" };
        int startX = indent;
        int endX = width - startX;
        int startY = firstLine + 7 * spacing;
        for (int i = 0; i < firstColumns.length; i++) {
            canvas.drawText(firstColumns[i], startX, startY, paint);
            canvas.drawLine(startX + paint.measureText(firstColumns[i]) , startY, endX, startY, paint);
            startY += spacing;
        }

        pdf.finishPage(page);

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/ket_qua_sat_hach.pdf");
            pdf.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pdf.close();
    }
}