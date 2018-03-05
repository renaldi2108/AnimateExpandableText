package id.renaldirey.animateexpandabletext;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kwikku on 02/03/2018.
 */

public class ExpandableText extends FrameLayout {
    private int collapsedHeight;
    private int expandHeight;
    private int animationDurations = 750;

    private String beforeText;
    private String afterText;

    private boolean expanded = false;
    private boolean animating = false;
    private boolean clicked = false;

    private TextView tvText;
    private ImageView iconExpandable;

    public ExpandableText(Context context) {
        super(context);
    }

    public ExpandableText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExpandableText(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayoutParams(new ViewGroup.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)));

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View parentView = inflater.inflate(R.layout.expand_text, this, true);
        tvText = (TextView) parentView.findViewById(R.id.tv_expand);
        iconExpandable = (ImageView) parentView.findViewById(R.id.icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setOutlineProvider(ViewOutlineProvider.BOUNDS);
        }

        setClipToPadding(false);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (tvText.getText().toString().equals(this.beforeText)){
            this.expandHeight = tvText.getMeasuredHeight();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        if (!clicked) {
            if (this.beforeText.length() > 250) {
                this.afterText = this.beforeText.subSequence(0, 250) + "...";

                tvText.setText(this.afterText);
                this.collapsedHeight = tvText.getMeasuredHeight();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                tvText.setText(this.beforeText);
                this.collapsedHeight = 0;
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    public ExpandableText addText(String texts) {
        this.beforeText = texts;

        tvText.setText(texts);
        return this;
    }

    public ExpandableText setAnimateDuration(int animateDuration) {
        this.animationDurations = animateDuration;
        return this;
    }

    public boolean expand(){
        if (!this.expanded && !this.animating) {
            this.clicked = true;
            this.animating = true;
            this.expanded = true;
            this.collapsedHeight = tvText.getHeight();

            final ValueAnimator valueAnimator = ValueAnimator.ofInt(this.collapsedHeight, this.expandHeight);
            final String beforesTxt = this.beforeText;
            final int expandedHeights = this.expandHeight;

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    tvText.setHeight((int) animation.getAnimatedValue());
                    tvText.setText(beforesTxt);
                }
            });

//            // start the animation

            valueAnimator
                    .setDuration(this.animationDurations)
                    .start();

            this.iconExpandable.animate().rotation(180).start();

            return true;
        }
        return false;
    }

    public boolean collapse() {
        if(this.expanded && this.animating) {
            this.clicked = true;
            this.animating = false;
            this.expanded = false;

            final ValueAnimator valueAnimator = ValueAnimator.ofInt(this.expandHeight, this.collapsedHeight);
            final int collapsesHeight = this.collapsedHeight;
            final String afterTxt = this.afterText;

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    tvText.setHeight((int) animation.getAnimatedValue());
                    tvText.setText(afterTxt);
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                }
            });

            // start the animation
            valueAnimator
                    .setDuration(this.animationDurations)
                    .start();

            this.iconExpandable.animate().rotation(0).start();

            return true;
        }
        return false;
    }

    public boolean isExpanded() {
        return this.expanded;
    }
}