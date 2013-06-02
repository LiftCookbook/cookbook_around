Code examples for the Around Lift chapter of the Lift Cookbook
================================================

This project contains examples from the [Lift Cookbook](http://shop.oreilly.com/product/0636920029151.do), online at [http://cookbook.liftweb.net/](http://cookbook.liftweb.net/).

To run this application:

* Launch SBT with: `./sbt` or `sbt.bat`

* In SBT, `container:start`

* Visit http://127.0.0.1:8080/ in your browser.

For the email examples, note that `Boot.scala` is configured to just log
email.  To really send, remove that configuration, and set up
valid from and to addresses in `src/main/resources/props/default.props`.

Credits:

* The image `src/main/resources/Kepler-22b_System_Diagram.jpg` is from Wikimedia and in the
public domain. See: http://commons.wikimedia.org/wiki/File:Kepler-22b_System_Diagram.jpg

