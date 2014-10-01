<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/"> 
  	<html>
  			<body>
  				<h1>Employees Of The Month</h1>
          <table id="employees">
            <thead>
              <tr>
                <th>Full Name</th>
                <th>Profile Picture</th>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="employees/employee">
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
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
  			</body>
  	</html>
  </xsl:template>
</xsl:stylesheet>
