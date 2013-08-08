<SSA_Scheduling_SOA_Message xmlns="http://rr.ets.org/v200609/schemas">
	<requestHeader>
		<action>SSA.CancelAppointment</action>
		<transactionID>$transactionId$</transactionID>
		<userRoleType>$userRoleType$</userRoleType>
		<source>$source$</source>
		<programCode>$programCode$</programCode>
		<requestsData>
			<dataElements>
				<name>CancelAppointment.SiteId</name>
				<data>
					<string>$siteId$</string>
				</data>
			</dataElements>
			<dataElements>
				<name>CancelAppointment.ExamId</name>
				<data>
					<string>$testCode$</string>
				</data>
			</dataElements>
			<dataElements>
				<name>Requesting.System</name>
				<data>
					<string>$systemId$</string>
				</data>
			</dataElements>
			<dataElements>
				<name>CancelAppointment.CancelCode</name>
				<data>
					<string>$cancelCode$</string>
				</data>
			</dataElements>
		</requestsData>
	</requestHeader>
	<candidates>
		<candidate>
			<clientCandidateID>$clientCandidateID$</clientCandidateID>
		</candidate>
	</candidates>
	<bookings>
		<booking>
			<clientAppointmentID>$clientAppointmentID$</clientAppointmentID>
		</booking>
	</bookings>
</SSA_Scheduling_SOA_Message>