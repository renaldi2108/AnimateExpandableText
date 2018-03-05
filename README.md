# AnimateExpandableText

## Installation
Add it in your root build.gradle at the end of repositories:

```
allprojects {
   repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
```

Add the dependency

```
compile 'com.github.renaldi2108:AnimateExpandableText:1.0.0'
```

## Usage
expand and collapse:
```java
final ExpandableText text = (ExpandableText) findViewById(R.id.expandabletext);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.isExpanded()) {
                    text.collapse();
                } else {
                    text.expand();
                }
            }
        });
```

use xml:
```xml
<id.renaldirey.animateexpandabletext.ExpandableText
        android:id="@+id/expandabletext"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

add text and set animate duration:
```java
text.addText("There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making");
text.setAnimateDuration(750);
```