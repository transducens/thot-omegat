Thot plugin for OmegaT

Compile with 

```
ant jar
```

(or download from the build folder) and copy build/jar/forecat-omegat.jar and the contents of lib into the plugins folder of OmegaT.

###OmegaT bug

In the last stable version (3.6.0.2) of [OmegaT](https://sourceforge.net/projects/omegat/) there is a bug that prevents the correct rendering of the suggestions on the Autocomplete popup. A temporary workaround (that breaks the rest of autocompleters) can be achieved patching Autocompleter.java with the following code

```
	@Override
	public void addView(AbstractAutoCompleterView view) {
		view.setParent(this);
		views.add(0,view);  // Instead of the original .add(view)
	}
```
