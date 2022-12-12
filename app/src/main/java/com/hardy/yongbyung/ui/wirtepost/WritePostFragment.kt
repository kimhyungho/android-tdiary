package com.hardy.yongbyung.ui.wirtepost

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hardy.yongbyung.R
import com.hardy.yongbyung.databinding.FragmentWritePostBinding
import com.hardy.yongbyung.dialog.FindLocationDialog
import com.hardy.yongbyung.ui.base.BaseViewModelFragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritePostFragment : BaseViewModelFragment<FragmentWritePostBinding, WritePostViewModel>(
    R.layout.fragment_write_post
), OnMapReadyCallback {
    override val viewModel: WritePostViewModel by viewModels()

    private var mNaverMap: NaverMap? = null
    private var mapView: MapView? = null
    private var marker: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.map_fragment)
        mapView?.getMapAsync(this)
        mapView?.onCreate(savedInstanceState)

        with(viewDataBinding) {
            locationContainer.setOnClickListener {
                val dialog = FindLocationDialog.newInstance { place ->
                    viewModel?.onPlaceConfirmButtonClick(place)
                }
                dialog.show(childFragmentManager, FindLocationDialog.TAG)
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                error.collect {
                    Snackbar.make(viewDataBinding.rootLayout, it, Snackbar.LENGTH_SHORT).show()
                }
            }

            lifecycleScope.launchWhenStarted {
                showPostDetail.collect {
                    if (!it.isNullOrEmpty()) navController
                        .navigate(
                            WritePostFragmentDirections
                                .actionDestWritePostToDestPostDetail(it)
                        )
                }
            }
        }
    }

    override fun onDestroyView() {
        mapView?.onDestroy()
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onMapReady(naverMap: NaverMap) {
        mNaverMap = naverMap.apply {
            with(uiSettings) {
                isScrollGesturesEnabled = false
                isTiltGesturesEnabled = false
                isCompassEnabled = false
                isLogoClickEnabled = false
                isIndoorLevelPickerEnabled = false
                isStopGesturesEnabled = false
                isZoomGesturesEnabled = false
                isLocationButtonEnabled = false
            }
        }

        with(viewModel) {
            lifecycleScope.launchWhenStarted {
                place.collect { place ->
                    if (place?.x != null && place.y != null) {
                        val latLng = LatLng(place.y!!, place.x!!)
                        mNaverMap?.moveCamera(CameraUpdate.scrollTo(latLng))
                        marker?.map = null
                        marker = Marker(latLng).apply {
                            map = mNaverMap
                        }
                    }
                }
            }
        }
    }
}