# RatingBar
Android library to animate rating bar<br>
[![](https://jitpack.io/v/damanpreetsb/RatingBar.svg)](https://jitpack.io/#damanpreetsb/RatingBar)

### DEMO
<br>
<p align="start">
<img src="https://github.com/damanpreetsb/RatingBar/blob/master/preview/demo.gif?raw=true" width="250">
</p>

### DOWNLOAD
<p>Add this to your root <code>build.gradle</code> file</p>

<pre><code>allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
</code></pre>

<p>Add this to your app module's <code>build.gradle</code> file</p>

<pre><code>dependencies {
            implementation 'com.github.damanpreetsb:RatingBar:v1.0'
    }
</code></pre>
<br>

### USAGE
<pre><code> &lt;com.daman.library.SimpleRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:total_stars="3"
        app:animation="alpha" /&gt;</code></pre>
        
#### Available animations are:
<ul>
<li><code>alpha</code></li>
<li><code>rotation</code></li>
<li><code>scale</code></li>
<li><code>ring</code></li>
<li><code>flip</code></li>
</ul>

#### Change drawables
<pre><code> &lt;com.daman.library.SimpleRatingBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:total_stars="3"
        app:animation="alpha"
        app:drawable_empty="@drawable/ic_favorite_border_black_24dp"
        app:drawable_filled="@drawable/ic_favorite_black_24dp"  /&gt;</code></pre>


### License
<pre><code>Copyright 2018 Damanpreet Singh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

</code></pre>
