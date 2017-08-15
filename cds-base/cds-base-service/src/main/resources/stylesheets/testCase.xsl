<?xml version="1.0" encoding="UTF-8"?>

<!-- New XSLT document created with EditiX XML Editor (http://www.editix.com) 
	at Thu Aug 13 15:26:44 EDT 2015 -->
<xsl:stylesheet exclude-result-prefixes="map" version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:tc="https://fits.nist.gov/xml/"
	xmlns:map="urn:internal">
	<xsl:output method="html" indent="yes" />
	<xsl:variable name="smallcase" select="'abcdefghijklmnopqrstuvwxyz'" />
	<xsl:variable name="uppercase" select="'ABCDEFGHIJKLMNOPQRSTUVWXYZ'" />
	<xsl:template match="/tc:TestCase">
		<html>
			[STYLE]
			<body>
				<h1 class="head1">
					<xsl:value-of select="Name" />
				</h1>
				<h2 class="head2">Test Case Metadata</h2>
				<table class="table">
					<tr>
						<td>
							<strong>Name</strong>
						</td>
						<td>
							<xsl:value-of select="Name" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>ID</strong>
						</td>
						<td>
							<xsl:value-of select="@UID" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Group</strong>
						</td>
						<td>
							<xsl:value-of select="Group" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Description</strong>
						</td>
						<td>
							<xsl:value-of select="Description" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Last Changed in Test Plan version</strong>
						</td>
						<td>
							<xsl:value-of select="MetaData/version" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Change Log</strong>
						</td>
						<td>
							<xsl:value-of select="MetaData/ChangeLog" />
						</td>
					</tr>
					<tr>
						<td>
							<strong>Date Created</strong>
						</td>
						<td>
							<xsl:call-template name="dateTransformer">
								<xsl:with-param name="myDate" select="MetaData/dateCreated" />
							</xsl:call-template>
						</td>
					</tr>
					<tr>
						<td>
							<strong>Date Last Updated</strong>
						</td>
						<td>
							<xsl:call-template name="dateTransformer">
								<xsl:with-param name="myDate" select="MetaData/dateLastUpdated" />
							</xsl:call-template>
						</td>
					</tr>
				</table>
				<h2 class="head2">Test Case Data</h2>
				<h3 class="head3">Assessment Date and Patient Information</h3>
				<table class="table">
					<tr>
						<td>
							<strong>Assessment Date</strong>
						</td>
						<td>
							<xsl:choose>
								<xsl:when test="AssessmentDate/Fixed">
									<xsl:call-template name="dateTransformer">
										<xsl:with-param name="myDate"
											select="AssessmentDate/Fixed/@date" />
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									Relative (Today)
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
					<tr>
						<td>
							<strong>Date Of Birth</strong>
						</td>
						<td>
							<xsl:choose>
								<xsl:when test="Patient/DateOfBirth/Fixed">
									<xsl:call-template name="dateTransformer">
										<xsl:with-param name="myDate"
											select="Patient/DateOfBirth/Fixed/@date" />
									</xsl:call-template>
								</xsl:when>
								<xsl:otherwise>
									<xsl:apply-templates select="Patient/DateOfBirth/Relative" />
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
					<tr>
						<td>
							<strong>Gender</strong>
						</td>
						<td>
							<xsl:value-of select="Patient/Gender" />
						</td>
					</tr>
				</table>
				<h3 class="head3">Vaccination History and Expected Evaluations</h3>
				<xsl:apply-templates select="Events" />
				<div class="no-break">
					<h3 class="head3">Expected Forecasts</h3>
					<xsl:apply-templates select="Forecasts" />
				</div>
			</body>
		</html>
	</xsl:template>
	<xsl:template match="Relative">
		<table class="no-border table">
			<xsl:for-each select="Rule">
					<tr class="no-border">
						<td class="no-border">
							<xsl:if test="position() > 1">
								<strong>But Not Before</strong>
							</xsl:if>
						</td>
						<td class="no-border">
							<xsl:if test="@years != 0">
								<xsl:value-of select="@years" /> Years
							</xsl:if>
						</td>
						<td class="no-border">
							<xsl:if test="@months != 0">
								<xsl:value-of select="@months" /> Months
							</xsl:if>
						</td>
						<td class="no-border">
							<xsl:if test="@days != 0">
								<xsl:value-of select="@days" /> Days
							</xsl:if>
						</td>
						<td class="no-border">
							<xsl:choose>
								<xsl:when test="@years='0' and @months='0' and @days='0'">
									On
								</xsl:when>
								<xsl:when test="RelativeTo[@position='BEFORE']">
									Before
								</xsl:when>
								<xsl:when test="RelativeTo[@position='AFTER']">
									After
								</xsl:when>
								<xsl:otherwise>
									NOT_DEF
								</xsl:otherwise>
							</xsl:choose>
						</td>
						<td class="no-border">
							<xsl:choose>
								<xsl:when test="RelativeTo[@reference='VACCINATION']">
									Vaccination (ID # <xsl:value-of select="RelativeTo/@id" />)'s Date
								</xsl:when>
								<xsl:when test="RelativeTo[@reference='EVALDATE']">
									Assessment Date
								</xsl:when>
								<xsl:when test="RelativeTo[@reference='DOB']">
									Date Of Birth
								</xsl:when>
								<xsl:otherwise>
									NOT_DEF
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
			</xsl:for-each>
		</table>
	</xsl:template>
	<xsl:template match="Events">
		<xsl:for-each select="Event">
			<table class="table">
				<tr>
					<td colspan="2" class="event-title">
						#
						<xsl:value-of select="@ID + 1" />
						-
						<xsl:value-of select="Administred/@name" />
						- CVX :
						<xsl:value-of select="Administred/@cvx" />
						<xsl:if test="Administred/@mvx">
							MVX :
							<xsl:value-of select="Administred/@mvx" />
						</xsl:if>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Date Administred</strong>
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="EventDate/Fixed">
								<xsl:call-template name="dateTransformer">
									<xsl:with-param name="myDate" select="EventDate/Fixed/@date" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="EventDate/Relative" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Evaluations</strong>
					</td>
					<td>
						<xsl:apply-templates select="Evaluations" />
					</td>
				</tr>
			</table>
		</xsl:for-each>
	</xsl:template>
	<xsl:template match="Evaluations">
		<table class="table">
			<thead>
				<tr>
					<th>CVX</th>
					<th>name</th>
					<th>Status</th>
					<th>Reason</th>
				</tr>
			</thead>
			<tbody>
				<xsl:for-each select="Evaluation">
					<tr>
						<td><xsl:value-of select="Vaccine/@cvx" /></td>
						<td><xsl:value-of select="Vaccine/@name" /></td>
						<td><xsl:value-of select="@status" /></td>
						<td><xsl:value-of select="EvaluationReason/@value" /></td>
					</tr>
				</xsl:for-each>
			</tbody>
		</table>
	</xsl:template>

	<xsl:template match="Forecasts">
		<xsl:for-each select="Forecast">
			<table class="table">
				<tr>
					<td colspan="2" class="event-title">
						<xsl:value-of select="target/@name" />
						- CVX :
						<xsl:value-of select="target/@cvx" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>Series Status</strong>
					</td>
					<td>
						<xsl:value-of select="SerieStatus/@details" />
					</td>
				</tr>
				<tr>
					<td>
						<strong>Earliest Date</strong>
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="Earliest/Fixed">
								<xsl:call-template name="dateTransformer">
									<xsl:with-param name="myDate" select="Earliest/Fixed/@date" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="Earliest/Relative" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Recommended Date</strong>
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="Recommended/Fixed">
								<xsl:call-template name="dateTransformer">
									<xsl:with-param name="myDate" select="Recommended/Fixed/@date" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="Recommended/Relative" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
				<tr>
					<td>
						<strong>Past Due Date</strong>
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="PastDue/Fixed">
								<xsl:call-template name="dateTransformer">
									<xsl:with-param name="myDate" select="PastDue/Fixed/@date" />
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:apply-templates select="PastDue/Relative" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
			</table>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="dateTransformer">
		<xsl:param name="myDate" />
		<xsl:variable name="year" select="substring-before($myDate, '-')" />
		<xsl:variable name="month"
			select="substring-before(substring-after($myDate, '-'), '-')" />
		<xsl:variable name="day"
			select="substring-before(substring-after(substring-after($myDate, '-'), '-'), '-')" />
		<xsl:choose>
			<xsl:when test="$month = 1">
				<xsl:text>January</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 2">
				<xsl:text>February</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 3">
				<xsl:text>March</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 4">
				<xsl:text>April</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 5">
				<xsl:text>May</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 6">
				<xsl:text>June</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 7">
				<xsl:text>July</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 8">
				<xsl:text>August</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 9">
				<xsl:text>September</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 10">
				<xsl:text>October</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 11">
				<xsl:text>November</xsl:text>
			</xsl:when>
			<xsl:when test="$month = 12">
				<xsl:text>December</xsl:text>
			</xsl:when>
		</xsl:choose>

		<!-- <xsl:value-of select="$month" /> -->
		<xsl:text> </xsl:text>
		<xsl:value-of select="$day" />
		<xsl:text>, </xsl:text>
		<xsl:value-of select="$year" />
	</xsl:template>
</xsl:stylesheet>