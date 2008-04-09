<?xml version="1.0" encoding="UTF-8"?>

<!--
  FindBugs - Find bugs in Java programs
  Copyright (C) 2004,2005 University of Maryland
  Copyright (C) 2005, Chris Nappin
  
  This library is free software; you can redistribute it and/or
  modify it under the terms of the GNU Lesser General Public
  License as published by the Free Software Foundation; either
  version 2.1 of the License, or (at your option) any later version.
  
  This library is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  Lesser General Public License for more details.
  
  You should have received a copy of the GNU Lesser General Public
  License along with this library; if not, write to the Free Software
  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
-->

<!--
  A simple XSLT stylesheet to transform FindBugs XML results
  annotated with messages into HTML.

  If you want to experiment with modifying this stylesheet,
  or write your own, you need to generate XML output from FindBugs
  using a special option which lets it know to include
  human-readable messages in the XML.  Invoke the findbugs script
  as follows:

    findbugs -textui -xml:withMessages -project myProject.fb > results.xml

  Then you can use your favorite XSLT implementation to transform
  the XML output into HTML. (But don't use xsltproc. It generates well-nigh
  unreadable output, and generates incorrect output for the
  <script> element.)

  Authors:
  David Hovemeyer
  Chris Nappin (summary table)
-->

<xsl:stylesheet
	version="1.0"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output
	method="xml"
	omit-xml-declaration="yes"
	standalone="yes"
	doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
	indent="yes"
	encoding="UTF-8"/>

<xsl:variable name="literalNbsp">&amp;nbsp;</xsl:variable>

<!--xsl:key name="bug-category-key" match="/BugCollection/BugInstance" use="@category"/-->

<xsl:variable name="bugTableHeader">
	<tr class="tableheader">
		<th align="left">Code<xsl:value-of select="$literalNbsp" disable-output-escaping="yes"/></th>
		<th align="left">Warning</th>
	</tr>
</xsl:variable>

<xsl:template match="/">
	<html>
	<head>
		<title>FindBugs Report</title>
		<style type="text/css">
		.tablerow0 {
			background: #EEEEEE;
		}

		.tablerow1 {
			background: white;
		}

		.detailrow0 {
			background: #EEEEEE;
		}

		.detailrow1 {
			background: white;
		}

		.tableheader {
			background: #b9b9fe;
			font-size: larger;
		}

		.tablerow0:hover, .tablerow1:hover {
			background: #aaffaa;
		}

		.priority-1 {
		    color: red;
		    font-weight: bold;
		}
		.priority-2 {
		    color: orange;
		    font-weight: bold;
		}
		.priority-3 {
		    color: green;
		    font-weight: bold;
		}
		.priority-4 {
		    color: blue;
		    font-weight: bold;
		}
		</style>
		<script type="text/javascript">
			function toggleRow(elid) {
				if (document.getElementById) {
					element = document.getElementById(elid);
					if (element) {
						if (element.style.display == 'none') {
							element.style.display = 'block';
							//window.status = 'Toggle on!';
						} else {
							element.style.display = 'none';
							//window.status = 'Toggle off!';
						}
					}
				}
			}
		</script>
	</head>

	<xsl:variable name="unique-catkey" select="/BugCollection/BugCategory/@category"/>
	<!--xsl:variable name="unique-catkey" select="/BugCollection/BugInstance[generate-id() = generate-id(key('bug-category-key',@category))]/@category"/-->

	<body>

	<h1>FindBugs Report</h1>

	<h2>Project Information</h2>	
	<xsl:apply-templates select="/BugCollection/Project"/>

	<h2>Contents</h2>
	<ul>
		<xsl:for-each select="$unique-catkey">
			<xsl:sort select="." order="ascending"/>
			<xsl:variable name="catkey" select="."/>
			<xsl:variable name="catdesc" select="/BugCollection/BugCategory[@category=$catkey]/Description"/>
			
			<li><a href="#Warnings_{$catkey}"><xsl:value-of select="$catdesc"/> Warnings</a></li>
		</xsl:for-each>

		<li><a href="#Details">Details</a></li>
	</ul>

	<h1>Summary</h1>
	<table width="500" cellpadding="5" cellspacing="2">
	    <tr class="tableheader">
			<th align="left">Warning Type</th>
			<th align="right">Number</th>
		</tr>

		<xsl:for-each select="$unique-catkey">
			<xsl:sort select="." order="ascending"/>
			<xsl:variable name="catkey" select="."/>
			<xsl:variable name="catdesc" select="/BugCollection/BugCategory[@category=$catkey]/Description"/>
			<xsl:variable name="styleclass">
				<xsl:choose><xsl:when test="position() mod 2 = 1">tablerow0</xsl:when>
					<xsl:otherwise>tablerow1</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
		<tr class="{$styleclass}">
			<td><a href="#Warnings_{$catkey}"><xsl:value-of select="$catdesc"/> Warnings</a></td>
			<td align="right"><xsl:value-of select="count(/BugCollection/BugInstance[@category=$catkey])"/></td>
		</tr>
		</xsl:for-each>

		<xsl:variable name="styleclass">
			<xsl:choose><xsl:when test="count($unique-catkey) mod 2 = 0">tablerow0</xsl:when>
				<xsl:otherwise>tablerow1</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<tr class="{$styleclass}">
		    <td><b>Total</b></td>
		    <td align="right"><b><xsl:value-of select="count(/BugCollection/BugInstance)"/></b></td>
		</tr>
	</table>

	<h1>Warnings</h1>

	<p>Click on a warning row to see full context information.</p>

	<xsl:for-each select="$unique-catkey">
		<xsl:sort select="." order="ascending"/>
		<xsl:variable name="catkey" select="."/>
		<xsl:variable name="catdesc" select="/BugCollection/BugCategory[@category=$catkey]/Description"/>
			
		<xsl:call-template name="generateWarningTable">
			<xsl:with-param name="warningSet" select="/BugCollection/BugInstance[@category=$catkey]"/>
			<xsl:with-param name="sectionTitle"><xsl:value-of select="$catdesc"/> Warnings</xsl:with-param>
			<xsl:with-param name="sectionId">Warnings_<xsl:value-of select="$catkey"/></xsl:with-param>
		</xsl:call-template>
	</xsl:for-each>

	<h1><a name="Details">Details</a></h1>

	<xsl:apply-templates select="/BugCollection/BugPattern">
		<xsl:sort select="@abbrev"/>
		<xsl:sort select="ShortDescription"/>
	</xsl:apply-templates>

	</body>
	</html>
</xsl:template>

<xsl:template match="Project">
	<p>Project: <xsl:value-of select="@filename"/></p>
	<p>FindBugs version: <xsl:value-of select="/BugCollection/@version"/></p>
	
	<p>Code analyzed:</p>
	<ul>
		<xsl:for-each select="./Jar">
			<li><xsl:value-of select="text()"/></li>
		</xsl:for-each>
	</ul>
</xsl:template>

<xsl:template match="BugInstance">
	<xsl:variable name="warningId"><xsl:value-of select="generate-id()"/></xsl:variable>

	<tr class="tablerow{position() mod 2}" onclick="toggleRow('{$warningId}');">

	<td>
	    <span><xsl:attribute name="class">priority-<xsl:value-of select="@priority"/></xsl:attribute>
	        <xsl:value-of select="@abbrev"/>
        </span>
	</td>

	<td>
	<xsl:value-of select="LongMessage"/>
	</td>

	</tr>

	<!-- Add bug annotation elements: Class, Method, Field, SourceLine, Field -->
	<tr class="detailrow{position() mod 2}">
		<td/>
		<td>
			<p id="{$warningId}" style="display: none;">
				<a href="#{@type}">Bug type <xsl:value-of select="@type"/> (click for details)</a>
				<xsl:for-each select="./*/Message">
					<br/><xsl:value-of select="text()"/>
				</xsl:for-each>
			</p>
		</td>
	</tr>
</xsl:template>

<xsl:template match="BugPattern">
	<h2><a name="{@type}"><xsl:value-of select="@type"/>: <xsl:value-of select="ShortDescription"/></a></h2>
	<xsl:value-of select="Details" disable-output-escaping="yes"/>
</xsl:template>

<xsl:template name="generateWarningTable">
	<xsl:param name="warningSet"/>
	<xsl:param name="sectionTitle"/>
	<xsl:param name="sectionId"/>

	<h2><a name="{$sectionId}"><xsl:value-of select="$sectionTitle"/></a></h2>
	<table class="warningtable" width="100%" cellspacing="0">
		<xsl:copy-of select="$bugTableHeader"/>
		<xsl:apply-templates select="$warningSet">
			<xsl:sort select="@abbrev"/>
			<xsl:sort select="Class/@classname"/>
		</xsl:apply-templates>
	</table>
</xsl:template>

</xsl:stylesheet>

<!-- vim:set ts=4: -->
