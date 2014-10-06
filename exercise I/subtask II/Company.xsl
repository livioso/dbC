<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/"> 
  	<html>
  			<body>
  				<h1>Projects</h1>
          <table id="projects">
            <thead>
              <tr>
                <th>Full Name</th>
                <th>Profile Picture</th>
                <th>Projects</th>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="company/employees/employee">
                <tr>
                  <td>
                    <a>
                      <xsl:attribute name="href">
                        <xsl:value-of select="concat('mailto:', email)"/>
                      </xsl:attribute>
                      <xsl:value-of select="name"/>
                    </a>
                  </td>
                  <td>
                    <img style="width:90px;height:100px">
                      <xsl:attribute name="alt">
                        <xsl:value-of select="concat('This is', name)"/>
                      </xsl:attribute>
                      <xsl:attribute name="src">
                        <xsl:value-of select="./@profilepicture"/>
                      </xsl:attribute>
                    </img>
                  </td>
                  <td>
                    <xsl:variable name="identifier" select="./@id" />
                    <xsl:for-each select="../../projects/project/members/member[@idref=$identifier]/../..">
                      <xsl:value-of select="@name" /> <br />
                    </xsl:for-each>
                  </td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
  			</body>
  	</html>
  </xsl:template>
</xsl:stylesheet>
