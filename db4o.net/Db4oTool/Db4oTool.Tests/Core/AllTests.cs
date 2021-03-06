/* Copyright (C) 2010 Versant Inc.   http://www.db4o.com */
using System;
using Db4oUnit;

namespace Db4oTool.Tests.Core
{
	class AllTests : ReflectionTestSuite
	{
		protected override Type[] TestCases()
		{
			return new Type[]
				{
					typeof(ByNameTestCase),
					typeof(ByAttributeTestCase),
					typeof(ByFilterTestCase),
					typeof(ByNotAttributeTestCase),
					typeof(ContextVariableTestCase),
					typeof(DebugInformationTestSuite),
					typeof(ILPatternTestCase),
					typeof(InstallPerformanceCountersTestCase),
					typeof(CustomInstrumentationTestCase),
					typeof(PreserveDebugInfoTestCase),
					typeof(InstrumentingCFAssemblyTestCase),
					typeof(StackAnalyzerTestCase),
				};
		}
	}
}
