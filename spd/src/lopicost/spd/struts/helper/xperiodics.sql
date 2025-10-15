--Procedimiento: dbo.x_periodics

/* DROP PROCEDURE dbo.x_periodics
GO */

CREATE PROCEDURE dbo.x_periodics
AS
BEGIN

		SET NOCOUNT ON;

		--- comprobar hora actual

		DECLARE @hhActual INT = DATEPART(hh,GetDate());		--- valor numeric de hora actual (sense minuts, etc)
		DECLARE @mmActual INT = DATEPART(mi,GetDate());		--- valor numeric del minut actual

		/*********************************************************************************************************************/
			
		/* 28-nov-24	>>>>>   BD Consejo se importa en proceso aparte programado en "Programador de tareas" de Windows, en el servidor
						>>>>>   y se jecuta semanalmante, los sábados a las 6:05
		
		/*importar dades Consejo */	/* 27.nov.2024 ->  modificada horar de ejecución a las 06:02 */
		IF @hhActual = 06						--- entre
		  AND @mmActual BETWEEN 01 AND 06       ---		les 06:25 i les 06:30
			BEGIN
				IF  EXISTS (
							SELECT * 
							FROM sys.objects 
							WHERE object_id = OBJECT_ID(N'[dbo].[importaBDconsejo]') 
							  AND type in (N'P', N'PC')
							)
				EXECUTE [SPDAC].[dbo].[importaBDconsejo]
			END;
		*/

		IF @hhActual = 7						--- entre
		  AND @mmActual BETWEEN 20 AND 24       ---		les 07:20 i les 07:24
			BEGIN
				IF  EXISTS (
							SELECT * 
							FROM sys.objects 
							WHERE object_id = OBJECT_ID(N'[dbo].[creaTaulaNoFinanciats]') 
							  AND type in (N'P', N'PC')
							)
				EXECUTE [SPDAC].[dbo].[creaTaulaNoFinanciats]
			END;

			/*
		IF @hhActual = 9						--- entre
		  AND @mmActual BETWEEN 10 AND 14       ---		les 09:10 i les 09:14
			BEGIN
				IF  EXISTS (
							SELECT * 
							FROM sys.objects 
							WHERE object_id = OBJECT_ID(N'[dbo].[creaTaula_drugBarcodeMASTERS]') 
							  AND type in (N'P', N'PC')
							)
				EXECUTE [SPDAC].[dbo].[creaTaula_drugBarcodeMASTERS];
			END;
			*/

		



		/*   importar fichas residentes de Farmatic */
		IF @hhActual = 20						--- entre
		  AND @mmActual BETWEEN 40 AND 44 
			BEGIN
				BEGIN TRY
					EXECUTE [SPDAC].[dbo].[importaDatosResidentesFarmatic];
				END TRY begin catch end catch
			END;

		
		
		
		
--/*>>> 16-mar-2025		cada5minutos se ejecuta con RoboTask
		
		
		-- acciones a ejecutar cada 5 minutos

		IF @hhActual NOT IN (0, 1, 2, 3, 4, 5, 6
							---, 7	--- no executar entre les 6 i les 6:59
							)
			BEGIN
				/* ja no cal executar. Programa nou de manteniment
							EXECUTE [SPDAC].[dbo].[set_MarcasSPDpanyales_segunESTATUS];
							EXECUTE [SPDAC].[dbo].[actualizaApellidosNombrePacientes];

							EXECUTE [SPDAC].[dbo].[sp_insertaCodigosSeguridad];
				*/
				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLtractamentSIRE_desde_sqlIOFWIN] '';
				--END TRY begin CATCH end catch
/**				EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLtractamentSIRE_desde_sqlIOFWIN] 'bellamar';		*/
				/* EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLtractamentSIRE_desde_sqlIOFWIN] 'valls'; */
				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLpendentSIRE_desde_sqlIOFWIN] '';
				--END TRY begin CATCH end CATCH


				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLtractamentSIRE_noRecollits];
				--END TRY begin CATCH end catch

				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[SP_actualiza_CTLpendentSIRE_noRecollits];
				--END TRY begin CATCH end CATCH
				
				/* añadir incidencias devueltas por SIRE */
				--BEGIN TRY
					update dbo.ctl_consultaPendentDispensarSIRE 
					SET 
						p_COD_CPF = ctr.Codigo
						, p_DES_CPF = ctr.Descripcion
					from ctl_consultaPendentDispensarSIRE cpd
					join ctl_consultaTractamentSIRE ctr on ctr.CIP = cpd.CIP
					where ctr.Codigo IN ('------','000000')
					  and (p_COD_CPF <> ctr.Codigo OR p_DES_CPF <> ctr.Descripcion);
				--END TRY begin CATCH end catch
				
				/* captar recetas dispensadas, para saber lo que queda pendiente, y a quién. Se captan todos, tanto si son de residencia como si no */
				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaCodigosRecetas_dispensados_cast];
				--END TRY begin CATCH end CATCH

				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaCodigosRecetas_dispensados_barcelona1];
				--END TRY begin CATCH end CATCH

				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaCodigosRecetas_dispensados_tarragona1];
				--END TRY begin CATCH end catch

				/* captar productos dispensados a residentes, para informe de compras */
				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaProductos_dispensadosResis_cast];
				--END TRY begin CATCH end CATCH

				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaProductos_dispensadosResis_bcn1];
				--END TRY begin CATCH end CATCH

				--BEGIN TRY
					EXECUTE [SPDAC].[dbo].[insertaProductos_dispensadosResis_tarr1];
				--END TRY begin CATCH end catch



		
				--procés per actualitzar els camps SPD i bolquers en el cas dels Exitus
				/***
				IF @hhActual = 7						--- entre
				  AND @mmActual BETWEEN 50 AND 54       ---		les 07:50 i les 07:54
					BEGIN
				***/
						---> afegit 31-12-2018 14:33
						/*****************************************************
						IF  EXISTS (
									SELECT * 
									FROM sys.objects 
									WHERE object_id = OBJECT_ID(N'[dbo].[actualizaDatosEnLosExitus]') 
									  AND type in (N'P', N'PC')
									)
						---< afegit 31-12-2018 14:33
						EXECUTE [SPDAC].[dbo].[actualizaDatosEnLosExitus]
						******************************************************/
				/***	END ***/

				-----------  24-06-2018   borrar de [ctl_consultaTractamentSIRE] los tratamientos que no se han renovado desde hace más de tres días,
				-----------               previo paso al histórico
				/*
				BEGIN TRAN
					DECLARE @numDiasGuardarTractamentSIRE INT = 3;
				
					INSERT INTO SPDAC.dbo.hst_consultaTractamentSIRE
					SELECT GETDATE() as fechaHoraHistorico, * FROM ctl_consultaTractamentSIRE
					WHERE DATEDIFF(d,fechaHoraProceso,GETDATE()) > @numDiasGuardarTractamentSIRE;
					--- order by fechaHoraProceso desc

					DELETE ctl_consultaTractamentSIRE
					WHERE DATEDIFF(d,fechaHoraProceso,GETDATE()) > @numDiasGuardarTractamentSIRE;
				COMMIT TRAN;
				*/
		
			END;
---<<< 16-mar-2025		cada5minutos se ejecuta con RoboTask	*/




		IF @hhActual = 23						--- entre
		  AND @mmActual BETWEEN 20 AND 24       ---		les 04:20 i les 04:24
			BEGIN
				BEGIN TRAN;
				
				SET NOCOUNT ON;

				IF EXISTS (	SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tbl_comprasXventasXproveedor]') AND type in (N'U'))
					DROP table tbl_comprasXventasXproveedor;
				

				/* SELECTs cortos, para "despertar" conexiones */
				BEGIN TRY
					select TOP 100 * from [dbo].[SrvrFCIA_comprasXventasXproveedor]; WAITFOR DELAY '00:00:03'; select TOP 1000 * from [dbo].[SrvrFCIA_comprasXventasXproveedor] ORDER BY FechaExtraccion DESC; WAITFOR DELAY '00:00:03';
				END TRY begin catch end catch

				BEGIN TRY
					select TOP 100 * from [dbo].[SrvrBCN1_comprasXventasXproveedor]; WAITFOR DELAY '00:00:03'; select TOP 1000 * from [dbo].[SrvrBCN1_comprasXventasXproveedor]; WAITFOR DELAY '00:00:03';
				END TRY begin catch end catch
				
				BEGIN TRY
					select TOP 100 * from [dbo].[SrvrTARR1_comprasXventasXproveedor]; WAITFOR DELAY '00:00:03'; select TOP 1000 * from [dbo].[SrvrTARR1_comprasXventasXproveedor]; WAITFOR DELAY '00:00:03'; 
				END TRY begin catch end catch


				/* insertar registros en la tabla */
				BEGIN TRY
					SELECT u.* 
					INTO dbo.tbl_comprasXventasXproveedor
					FROM
						(
							(SELECT * from SRVRfcia_comprasXventasXproveedor) 
							UNION ALL
							(SELECT * from SRVRbcn1_comprasXventasXproveedor) 
							UNION ALL
							(SELECT * from SRVRtarr1_comprasXventasXproveedor)
						) u
				END TRY begin catch end catch

				/* crear índices */
				CREATE NONCLUSTERED INDEX [tbl_comprasXventasXproveedor_Index01] ON [dbo].[tbl_comprasXventasXproveedor]
					(
						[UPfarmacia] ASC
					)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY];

				CREATE NONCLUSTERED INDEX [tbl_comprasXventasXproveedor_Index02] ON [dbo].[tbl_comprasXventasXproveedor]
					(
						[cnCompra] ASC
					)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY];

				COMMIT TRAN;
							   
			END;
	

		--- ejecutar acciones según la hora que sea


		/* 2022.09.16  ahora se ejecuta dentro del soapICSclient
			IF @hhActual = 7						--- entre
			  AND @mmActual BETWEEN 50 AND 54       ---		les 04:20 i les 04:24
				BEGIN
					EXECUTE [dbo].[insertaCodigosRecetas_CADUCADAS]
				END;
		*/





/*
		--procés per actualitzar la taula d'històric de pendentDispensarSIRE
		IF @hhActual = 7						--- entre
		  AND @mmActual BETWEEN 50 AND 54       ---		les 06:50 i les 06:54
			BEGIN
				IF  EXISTS (
							SELECT * 
							FROM sys.objects 
							WHERE object_id = OBJECT_ID(N'[dbo].[actualizaHstConsultaPendentDispensarSIREAgrupado]') 
							  AND type in (N'P', N'PC')
							)
				EXECUTE [SPDAC].[dbo].[actualizaHstConsultaPendentDispensarSIREAgrupado]
			END;
*/
		final:


END;
GO