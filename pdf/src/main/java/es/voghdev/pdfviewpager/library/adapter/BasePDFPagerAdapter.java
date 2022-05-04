/*
 * Copyright (C) 2016 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.voghdev.pdfviewpager.library.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import es.voghdev.pdfviewpager.library.R;

public class BasePDFPagerAdapter extends PagerAdapter {
    protected static final int FIRST_PAGE = 0;
    protected static final float DEFAULT_QUALITY = 2.0f;
    protected static final int DEFAULT_OFFSCREENSIZE = 1;

    protected String pdfPath;
    protected Context context;
    protected PdfRenderer renderer;
    protected BitmapContainer bitmapContainer;
    protected LayoutInflater inflater;

    protected float renderQuality;
    protected int offScreenSize;
    protected PdfErrorHandler errorHandler = new NullPdfErrorHandler();

    public BasePDFPagerAdapter(Context context, String pdfPath) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = DEFAULT_OFFSCREENSIZE;

        init();
    }

    public BasePDFPagerAdapter(Context context, String pdfPath, PdfErrorHandler errorHandler) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = DEFAULT_OFFSCREENSIZE;
        if (errorHandler != null) {
            this.errorHandler = errorHandler;
        }

        init();
    }

    /**
     * This constructor was added for those who want to customize ViewPager's offScreenSize attr
     */
    public BasePDFPagerAdapter(Context context, String pdfPath, int offScreenSize) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = offScreenSize;

        init();
    }

    @SuppressWarnings("NewApi")
    protected void init() {
        try {
            renderer = new PdfRenderer(getSeekableFileDescriptor(pdfPath));
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            PdfRendererParams params = extractPdfParamsFromFirstPage(renderer, renderQuality);
            bitmapContainer = new SimpleBitmapPool(params);
        } catch (IOException e) {
            errorHandler.onPdfError(e);
        }
    }

    @SuppressWarnings("NewApi")
    protected PdfRendererParams extractPdfParamsFromFirstPage(PdfRenderer renderer, float renderQuality) {
        PdfRenderer.Page samplePage = getPDFPage(renderer, FIRST_PAGE);
        PdfRendererParams params = new PdfRendererParams();

        params.setRenderQuality(renderQuality);
        params.setOffScreenSize(offScreenSize);
        params.setWidth((int) (samplePage.getWidth() * renderQuality));
        params.setHeight((int) (samplePage.getHeight() * renderQuality));

        samplePage.close();

        return params;
    }

    protected ParcelFileDescriptor getSeekableFileDescriptor(String path) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;

        File pdfCopy = new File(path);

        if (pdfCopy.exists()) {
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
            return parcelFileDescriptor;
        }

        if (isAnAsset(path)) {
            pdfCopy = new File(context.getCacheDir(), path);
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
        } else {
            URI uri = URI.create(String.format("file://%s", path));
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(Uri.parse(uri.toString()), "rw");
        }

        return parcelFileDescriptor;
    }

    protected boolean isAnAsset(String path) {
        return !path.startsWith("/");
    }

    @Override
    @SuppressWarnings("NewApi")
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.view_pdf_page, container, false);
        ImageView iv = (ImageView) v.findViewById(R.id.imageView);


        if (renderer == null || getCount() < position) {
            return v;
        }

        PdfRenderer.Page page = getPDFPage(renderer, position);
        Bitmap bitmap = bitmapContainer.get(position);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

        iv.setImageBitmap(bitmap);
        container.addView(v, 0);

        return v;
    }

    @SuppressWarnings("NewApi")
    protected PdfRenderer.Page getPDFPage(PdfRenderer renderer, int position) {
        return renderer.openPage(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // bitmap.recycle() causes crashes if called here.
        // All bitmaps are recycled in close().
    }

    @SuppressWarnings("NewApi")
    public void close() {
        releaseAllBitmaps();
        if (renderer != null) {
            renderer.close();
        }
    }

    protected void releaseAllBitmaps() {
        if (bitmapContainer != null) {
            bitmapContainer.clear();
        }
    }

    @Override
    @SuppressWarnings("NewApi")
    public int getCount() {
        return renderer != null ? renderer.getPageCount() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

}
