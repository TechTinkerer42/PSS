<SSA_Scheduling_SOA_Message xmlns="http://rr.ets.org/v200609/schemas">
	<requestHeader>
		<action>SSA.HoldSeat</action>
		<transactionID>$transactionId$</transactionID>
		<userRoleType>$userRoleType$</userRoleType>
		<source>$source$</source>
		<programCode>$programCode$</programCode>
		<requestsData>
			<dataElements>
				<name>HoldSeat.ExamId</name>
				<data>
					<string>$testCode$</string>
				</data>
			</dataElements>
			<dataElements>
				<name>Requesting.System</name>
				<data>
					<string>$requestingSystem$</string>
				</data>
			</dataElements>
			<dataElements>
				<name>HoldSeat.TestDuration</name>
				<data>
					<duration>PT$duration$M</duration>
				</data>
			</dataElements>
			<dataElements>
				<name>HasAccommodations</name>
				<data>
					<boolean>$HasAccommodations$</boolean>
				</data>
			</dataElements>
			
			<dataElements>
 				<name>FindSeats.ALTERNATE_FORM</name>
 				<data>
 					<string>$alternateForm$</string>
 				</data>
 			</dataElements> 
			
		</requestsData>
	</requestHeader>

 	<candidates>
 		<candidate>
			$accommodations$
 		</candidate>
 	</candidates>

	<bookings>
		<booking>
			<slots>
				<slot>
					<siteCode>$siteCode$</siteCode>
					<labCode>$labCode$</labCode>
					<seatCode>$seatCode$</seatCode>
					<startTime>$startTime$</startTime>
				</slot>
			</slots>
		</booking>
	</bookings>
</SSA_Scheduling_SOA_Message>

